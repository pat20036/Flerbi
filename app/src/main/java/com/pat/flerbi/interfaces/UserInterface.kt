package com.pat.flerbi.interfaces

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.model.Tag
import com.pat.flerbi.view.auth.WelcomeActivity

interface UserInterface {
    fun getUserEmail(): String
    fun logoutUser()
    fun saveUserTags(tags: List<String>)
    fun getProfilePoints(): LiveData<String>
    fun getProfileAchievements(): LiveData<String>
    fun getProfileRecommends():LiveData<String>
    fun isUserActive():LiveData<Boolean>
    fun getProfileTags():LiveData<List<Tag>>

}

class UserInterfaceImpl(private val context: Context) : UserInterface {
    private val uid = FirebaseAuth.getInstance().uid!!
    override fun getUserEmail(): String = FirebaseAuth.getInstance().currentUser.email

    override fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context.applicationContext, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)

    }

    override fun saveUserTags(tags: List<String>) {
        val ref = FirebaseDatabase.getInstance().getReference("user-tags/$uid/tags")
        ref.setValue(tags)
    }

    override fun getProfilePoints(): LiveData<String> {
        val profilePoints = MutableLiveData<String>()
        val databaseReferenceProfilePoints =
            FirebaseDatabase.getInstance().getReference("registered-users/$uid/points")
        databaseReferenceProfilePoints.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profilePoints.value = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return profilePoints
    }

    override fun getProfileAchievements(): LiveData<String> {
        var profileAchievements = MutableLiveData<String>()
        val uid = FirebaseAuth.getInstance().uid
        val databaseReferenceProfilePoints =
            FirebaseDatabase.getInstance().getReference("registered-users/$uid/achievements")
        databaseReferenceProfilePoints.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profileAchievements.value = snapshot.value.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return profileAchievements
    }

    override fun getProfileRecommends(): LiveData<String> {
        val profileRecommends = MutableLiveData<String>()
        val databaseReferenceProfilePoints =
            FirebaseDatabase.getInstance().getReference("registered-users/$uid/recommends")
        databaseReferenceProfilePoints.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profileRecommends.value = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return profileRecommends
    }

    override fun isUserActive(): LiveData<Boolean> {
        val isUserActive = MutableLiveData<Boolean>()
        val database = FirebaseDatabase.getInstance().getReference("TestConnection")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isUserActive.value = true
            }

            override fun onCancelled(error: DatabaseError) {
                isUserActive.value = false
            }

        })
        return isUserActive
    }

    override fun getProfileTags(): LiveData<List<Tag>> {
        val myTagsLiveData = MutableLiveData<List<Tag>>()
        val myTags = mutableListOf<Tag>()
        val ref = FirebaseDatabase.getInstance().getReference("user-tags/$uid/tags")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val count = snapshot.childrenCount
                if (count == 0L) {
                    myTagsLiveData.value = emptyList()
                }
                for (i in 0 until count) {
                    val databaseReference =
                        FirebaseDatabase.getInstance().getReference("user-tags/$uid/tags/$i")
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val dane = snapshot.value.toString()
                            myTags.clear()
                            myTags.add(Tag(dane))
                            myTagsLiveData.value = myTags
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    return myTagsLiveData
    }



}