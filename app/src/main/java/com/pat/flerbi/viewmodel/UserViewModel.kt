package com.pat.flerbi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.OnlineOfflineInterface
import com.pat.flerbi.SharedPreferencesInterface
import com.pat.flerbi.interfaces.UserInterface
import com.pat.flerbi.model.Tag

class UserViewModel(
    private val userInterface: UserInterface,
    private val sharedPreferencesInterface: SharedPreferencesInterface,
    private val databaseInterface: OnlineOfflineInterface
) : ViewModel() {

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

    private val _profileTags = MutableLiveData<List<Tag>>()
    val profileTags: LiveData<List<Tag>> get() = _profileTags

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    fun getUserNickname() {
        _userNickname.value = sharedPreferencesInterface.getUserNickname()
    }

    fun getActiveUsersCount() {
        _usersCount.value = sharedPreferencesInterface.getActiveUsersCount()
    }

    fun getUserEmail() {
        _userEmail.value = userInterface.getUserEmail()
    }

    fun addToActiveUsers() {
        databaseInterface.addToActiveUsers()
    }

    fun removeFromActiveUsers() {
        databaseInterface.removeFromActiveUsers()
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun isUserActive() {
        userInterface.isUserActive().observeForever(Observer {
            _isUserActive.value = it
        })
    }

    fun getProfilePoints() {
        userInterface.getProfilePoints().observeForever(Observer {
            _profilePoints.value = it
        })
    }


    fun getProfileAchievements() {
        userInterface.getProfileAchievements().observeForever(Observer {
            _profileAchievements.value = it
        })
    }

    fun getProfileRecommends() {
        userInterface.getProfileRecommends().observeForever(Observer {
            _profileRecommends.value = it
        })
    }


    fun getProfileTags() {
        userInterface.getProfileTags().observeForever(Observer {
            _profileTags.value = it
        })

    }

    fun saveUserTags(tags: List<String>) {
        userInterface.saveUserTags(tags)
    }


}
