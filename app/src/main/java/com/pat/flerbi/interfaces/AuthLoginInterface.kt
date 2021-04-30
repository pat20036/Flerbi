package com.pat.flerbi

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.model.RegisterData
import com.pat.flerbi.view.main.MainActivity

interface AuthLoginInterface {
    fun dataValidator(email: String, password: String)
}

class AuthLoginInterfaceImpl(private val context: Context) : AuthLoginInterface {
    private val sharedPreferences =
        context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

    override fun dataValidator(
        email: String,
        password: String,
    ) {
        if (email.isBlank() || password.isBlank() || password.length < 6) {
            if (email.isBlank()) {
                //error
            }
            if (password.isBlank()) {
                //error
            }

        } else {
            loginUser(email, password)
        }

    }

    private fun loginUser(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                    val ref = FirebaseDatabase.getInstance().getReference("registered-users/$uid")
                    ref.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val dane = snapshot.getValue(RegisterData::class.java)
                            if (dane != null) {
                                val nickname = dane.nickname
                                sharedPreferences.edit().putString("nick", nickname).apply()
                                if (it.isSuccessful) {
                                    val intent =
                                        Intent(context.applicationContext, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
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
                        // pass_login_text.error = "Hasło powinno mieć conajmniej 6 znaków"
                    }
                    is FirebaseAuthEmailException -> {
                        //   email_login_text.error = "Błędny email"
                    }
                    is FirebaseAuthInvalidUserException -> {
                        // Toast.makeText(context, "Nie znaleziono użytkownika", Toast.LENGTH_SHORT).show()
                    }
                }


            }
    }

}