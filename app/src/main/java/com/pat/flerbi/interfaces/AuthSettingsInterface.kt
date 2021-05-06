package com.pat.flerbi.interfaces

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.R
import com.pat.flerbi.view.auth.WelcomeActivity

interface AuthSettingsInterface {
    fun confirmIdentity(email: String, password: String): LiveData<Boolean>
    fun changePassword(password: String): LiveData<Boolean>
    fun deleteAccount(nickname: String): LiveData<Boolean>
}

class AuthSettingsInterfaceImpl(private val context: Context) : AuthSettingsInterface {

    private val firebaseInstance = FirebaseDatabase.getInstance()
    private val sharedPreferences =
        context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

    override fun confirmIdentity(email: String, password: String): LiveData<Boolean> {
        val responseLiveData = MutableLiveData<Boolean>()
        val user = FirebaseAuth.getInstance().currentUser

        if (password.isNotBlank()) {
            val credential = EmailAuthProvider.getCredential(email, password)
            user?.reauthenticate(credential)?.addOnCompleteListener()
            {
                if (it.isSuccessful) {
                    responseLiveData.value = true
                }
            }?.addOnFailureListener()
            {
                when (it) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        responseLiveData.value = false
                    }
                }
            }
        } else responseLiveData.value = false

        return responseLiveData
    }

    override fun changePassword(password: String): LiveData<Boolean> {
        val responseLiveData = MutableLiveData<Boolean>()
        val user = FirebaseAuth.getInstance().currentUser

        if (password.isNotBlank()) {
            user.updatePassword(password).addOnSuccessListener {
                responseLiveData.value = true
            }.addOnFailureListener {
                responseLiveData.value = false
            }

        } else responseLiveData.value = false

        return responseLiveData
    }

    override fun deleteAccount(nickname: String): LiveData<Boolean> {
        val responseLiveData = MutableLiveData<Boolean>()
        val user = FirebaseAuth.getInstance().currentUser

        user?.delete()?.addOnCompleteListener {
            if (it.isSuccessful) {
                deleteFromActiveUsers()
                deleteFromDatabase()
                deleteUserTags()
                deleteNickname(nickname)

                sharedPreferences.edit().clear().apply()
                val intent = Intent(context.applicationContext, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
        }?.addOnFailureListener {
            responseLiveData.value = false
        }
        return responseLiveData
    }

    private fun deleteFromActiveUsers() {
        val uid = FirebaseAuth.getInstance().uid
        firebaseInstance.getReference("active-users/$uid").removeValue()

    }

    private fun deleteFromDatabase() {
        val uid = FirebaseAuth.getInstance().uid
        firebaseInstance.getReference("registered-users/$uid").removeValue()

    }

    private fun deleteUserTags() {
        val uid = FirebaseAuth.getInstance().uid
        firebaseInstance.getReference("user-tags/$uid").removeValue()
    }


    private fun deleteNickname(nickname: String) {
        firebaseInstance.getReference("nicknames/$nickname").removeValue()
    }


}
