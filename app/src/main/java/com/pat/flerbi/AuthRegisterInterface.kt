package com.pat.flerbi

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.model.RegisterData

interface AuthRegisterInterface {
    fun dataValidator(
        email: String,
        password: String,
        rePassword: String,
        nickname: String,
        touCheckBox: Boolean
    )
    fun isNicknameFree(email: String,password: String, nickname: String)
    fun addNicknameToDatabase(nickname: String)
    fun registerUser(email: String, password: String, nickname: String)
    fun addUserToDatabase(nickname: String)
}

class AuthRegisterInterfaceImpl(private val context: Context) : AuthRegisterInterface {
    private val sharedPreferences =
        context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

    override fun dataValidator(
        email: String,
        password: String,
        rePassword: String,
        nickname: String,
        touCheckBox: Boolean
    ) {
        if (email.isBlank() || password.isBlank() || password.length < 6 || password != rePassword || rePassword.isBlank() || !touCheckBox) {

            if (password != rePassword) {
               // errorMessage("Passwords do not match")
            }

            if (!touCheckBox) {
               // errorMessage("Accept Terms of Use")
            }

        } else {
            isNicknameFree(email,password,nickname)
        }

    }

    override fun isNicknameFree(email: String, password: String, nickname: String) {
        val database = FirebaseDatabase.getInstance().getReference("nicknames").child(nickname)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                   // errorMessage("Username taken")
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
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            }
            .addOnFailureListener {
                Log.d("Main", "Fail to create user: ${it.message}")
                when (it) {
                    is FirebaseAuthUserCollisionException -> {
                     // "Account with this email already exists"
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

