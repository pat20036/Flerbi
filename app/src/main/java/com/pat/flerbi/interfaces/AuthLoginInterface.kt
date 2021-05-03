package com.pat.flerbi.interfaces

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.model.LoginError
import com.pat.flerbi.model.RegisterData
import com.pat.flerbi.view.main.MainActivity

interface AuthLoginInterface {
    fun dataValidator(email: String, password: String): LiveData<List<LoginError>>
}

class AuthLoginInterfaceImpl(private val context: Context) : AuthLoginInterface {
    private val sharedPreferences =
        context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
    private val errorLiveData = MutableLiveData<List<LoginError>>()
    private val errorList = mutableListOf<LoginError>()
    override fun dataValidator(
        email: String,
        password: String,
    ): LiveData<List<LoginError>> {
        errorList.clear()
        if (email.isBlank() || password.isBlank() || password.length < 6) {
            if (email.isBlank()) {
                errorList.add(LoginError(0, "Email cannot be blank"))
                errorLiveData.value = errorList
            }
            if (password.isBlank()) {
                errorList.add(LoginError(1, "Password cannot be blank"))
                errorLiveData.value = errorList
            }
            if (password.length < 6) {
                errorList.add(LoginError(2, "Incorrect password"))
                errorLiveData.value = errorList
            }

        } else {
            loginUser(email, password)
        }

        return errorLiveData
    }

    private fun loginUser(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                    val ref = FirebaseDatabase.getInstance().getReference("registered-users/$uid")
                    ref.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val data = snapshot.getValue(RegisterData::class.java)
                            if (data != null) {
                                val nickname = data.nickname
                                sharedPreferences.edit().putString("nick", nickname).apply()
                                if (it.isSuccessful) {
                                    val intent =
                                        Intent(context.applicationContext, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)

                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            .addOnFailureListener()
            {
                when (it) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        errorList.add(LoginError(3, "Incorrect password"))
                        errorLiveData.value = errorList
                    }
                    is FirebaseAuthEmailException -> {
                        errorList.add(LoginError(4, "Incorrect email"))
                        errorLiveData.value = errorList
                    }
                    is FirebaseAuthInvalidUserException -> {
                        errorList.add(LoginError(5, "User not found"))
                        errorLiveData.value = errorList
                    }
                }
            }
    }

}