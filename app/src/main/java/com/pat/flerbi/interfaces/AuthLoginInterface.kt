package com.pat.flerbi.interfaces

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.R
import com.pat.flerbi.model.LoginError
import com.pat.flerbi.model.RegisterData
import com.pat.flerbi.view.main.MainActivity

interface AuthLoginInterface {
    fun dataValidator(email: String, password: String): LiveData<List<LoginError>>
    fun remindPassword(email: String): LiveData<Boolean>
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
                errorList.add(LoginError.EMPTY_EMAIL)
                errorLiveData.value = errorList
            }
            if (password.isBlank()) {
                errorList.add(LoginError.EMPTY_PASSWORD)
                errorLiveData.value = errorList
            }
            if (password.length < 6) {
                errorList.add(LoginError.PASSWORD_LENGTH)
                errorLiveData.value = errorList
            }

        } else {
            loginUser(email, password)
        }

        return errorLiveData
    }

    override fun remindPassword(email: String): LiveData<Boolean> {
        val remindLiveData = MutableLiveData<Boolean>()
        if (email.isNotBlank()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        remindLiveData.value = true
                    }
                }.addOnFailureListener {
                    when (it) {
                        is FirebaseAuthInvalidUserException -> {
                            remindLiveData.value = false
                        }
                    }
                }
        } else remindLiveData.value = false

        return remindLiveData
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
                        errorList.add(LoginError.INCORRECT_PASSWORD)
                        errorLiveData.value = errorList
                    }
                    is FirebaseAuthEmailException -> {
                        errorList.add(LoginError.INCORRECT_EMAIL)
                        errorLiveData.value = errorList
                    }
                    is FirebaseAuthInvalidUserException -> {
                        errorList.add(LoginError.USER_NOT_FOUND)
                        errorLiveData.value = errorList
                    }
                }
            }
    }

}