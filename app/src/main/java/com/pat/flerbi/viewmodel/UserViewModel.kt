package com.pat.flerbi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.OnlineOfflineInterface
import com.pat.flerbi.SharedPreferencesInterface
import com.pat.flerbi.UserInterface

class UserViewModel(
    private val userInterface: UserInterface,
    private val sharedPreferencesInterface: SharedPreferencesInterface,
    private val databaseInterface: OnlineOfflineInterface
) : ViewModel() {

    private val uid = FirebaseAuth.getInstance().uid
    private val _isUserActive = MutableLiveData<Boolean>()
    val isUserActive: LiveData<Boolean> get() = _isUserActive

    private val _profilePoints = MutableLiveData<String>()
    val profilePoints: LiveData<String> get() = _profilePoints

    private val _profileRecommends = MutableLiveData<String>()
    val profileRecommends: LiveData<String> get() = _profileRecommends

    private val _profileAchievements = MutableLiveData<String>()
    val profileAchievements: LiveData<String> get() = _profileAchievements

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

    private val _usersCount = MutableLiveData<String>()
    val usersCount: LiveData<String> get() = _usersCount

    private val _profileTags = MutableLiveData<List<String>>()
    val profileTags: LiveData<List<String>> get() = _profileTags

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    fun getUserNickname() {
        _userNickname.value = sharedPreferencesInterface.getUserNickname()
    }

    fun getActiveUsersCount() {
        _usersCount.value = sharedPreferencesInterface.getActiveUsersCount()
    }

    fun getUserEmail()
    {
       _userEmail.value = userInterface.getUserEmail()
    }

    fun addToActiveUsers() {
        databaseInterface.addToActiveUsers()
    }
    fun removeFromActiveUsers()
    {
        databaseInterface.removeFromActiveUsers()
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun isUserActive() {
        _isUserActive.value = false
        val database = FirebaseDatabase.getInstance().getReference("TestConnection")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _isUserActive.value = true
            }

            override fun onCancelled(error: DatabaseError) {
                _isUserActive.value = false
            }

        })
    }

    fun getProfilePoints() {

        val databaseReferenceProfilePoints =
            FirebaseDatabase.getInstance().getReference("registered-users/$uid/points")
        databaseReferenceProfilePoints.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                _profilePoints.value = snapshot.value.toString()
                Log.d("PKT", profilePoints.value.toString())

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    fun getProfileRecommends() {
        val databaseReferenceProfilePoints =
            FirebaseDatabase.getInstance().getReference("registered-users/$uid/recommends")
        databaseReferenceProfilePoints.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                _profileRecommends.value = snapshot.value.toString()
                Log.d("PKT", profilePoints.value.toString())

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    fun getProfileAchievements() {
        val uid = FirebaseAuth.getInstance().uid
        val databaseReferenceProfilePoints =
            FirebaseDatabase.getInstance().getReference("registered-users/$uid/achievements")
        databaseReferenceProfilePoints.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                _profileAchievements.value = snapshot.value.toString()
                Log.d("PKT", profilePoints.value.toString())

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


    fun getProfileTags() {

        val uid = FirebaseAuth.getInstance().uid
        val myTags = mutableListOf<String>()
        val ref = FirebaseDatabase.getInstance().getReference("user-tags/$uid/tags")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val count = snapshot.childrenCount
                Log.d("ILE", count.toString())
                if (count == 0L) {
                    _profileTags.value = emptyList()
                }
                for (i in 0 until count) {
                    val databaseReference =
                        FirebaseDatabase.getInstance().getReference("user-tags/$uid/tags/$i")
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val dane = snapshot.value.toString()
                            myTags.clear()
                            myTags.add(dane)
                            _profileTags.value = myTags
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }


}
