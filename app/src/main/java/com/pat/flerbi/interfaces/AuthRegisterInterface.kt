package com.pat.flerbi.interfaces

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.model.LoginError
import com.pat.flerbi.model.RegisterData
import com.pat.flerbi.model.RegisterError
import com.pat.flerbi.view.main.MainActivity

interface AuthRegisterInterface {
    fun dataValidator(
        email: String,
        password: String,
        rePassword: String,
        nickname: String,
        touCheckBox: Boolean
    ): LiveData<List<RegisterError>>

    fun isNicknameFree(email: String, password: String, nickname: String)
    fun addNicknameToDatabase(nickname: String)
    fun registerUser(email: String, password: String, nickname: String)
    fun addUserToDatabase(nickname: String)

}

class AuthRegisterInterfaceImpl(private val context: Context) : AuthRegisterInterface {
    private val sharedPreferences =
        context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
    private val errorLiveData = MutableLiveData<List<RegisterError>>()
    private val errorList = mutableListOf<RegisterError>()

    override fun dataValidator(
        email: String,
        password: String,
        rePassword: String,
        nickname: String,
        touCheckBox: Boolean
    ): LiveData<List<RegisterError>> {
        errorList.clear()
        if (email.isBlank() || password.isBlank() || password.length < 6 || password != rePassword || rePassword.isBlank() || !touCheckBox) {

            if (password != rePassword) {
                errorList.add(RegisterError.WRONG_PASSWORD)
                errorLiveData.value = errorList
            }

            if (!touCheckBox) {
                errorList.add(RegisterError.ACCEPT_TERMS)
                errorLiveData.value = errorList
            }

        } else {
            isNicknameFree(email, password, nickname)
        }
        return errorLiveData
    }

    override fun isNicknameFree(email: String, password: String, nickname: String) {
        val database = FirebaseDatabase.getInstance().getReference("nicknames").child(nickname)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    errorList.add(RegisterError.NICKNAME_TAKEN)
                    errorLiveData.value = errorList
                } else {
                    registerUser(email, password, nickname)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //error
            }
        })
    }

    override fun addNicknameToDatabase(nickname: String) {
        val database = FirebaseDatabase.getInstance().getReference("nicknames").child(nickname)
        database.setValue(nickname)
    }

    override fun registerUser(email: String, password: String, nickname: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    addUserToDatabase(nickname)
                    addNicknameToDatabase(nickname)
                    sharedPreferences.edit().putString("nick", nickname).apply()
                    val intent = Intent(context.applicationContext, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    context.startActivity(intent)
                }
            }
            .addOnFailureListener {
                Log.d("Main", "Fail to create user: ${it.message}")
                when (it) {
                    is FirebaseAuthUserCollisionException -> {
                        errorList.add(RegisterError.EMAIL_TAKEN)
                        errorLiveData.value = errorList
                    }
                }
            }
    }

    override fun addUserToDatabase(nickname: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val ref = FirebaseDatabase.getInstance().getReference("registered-users/$uid")
            ref.setValue(RegisterData(nickname, uid, 0, 0, 0, 0))
        }
    }


}

