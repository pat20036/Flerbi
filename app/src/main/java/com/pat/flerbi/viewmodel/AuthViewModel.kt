package com.pat.flerbi.viewmodel

import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pat.flerbi.AuthLoginInterface
import com.pat.flerbi.AuthRegisterInterface

class AuthViewModel(private val authRegisterInterface: AuthRegisterInterface, private val authLoginInterface: AuthLoginInterface): ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage:LiveData<String> get() = _errorMessage

    fun registerUser(email:String, password:String, rePassword: String, nickname: String, touCheckBox: Boolean)
    {
        authRegisterInterface.dataValidator(email, password, rePassword, nickname, touCheckBox)
    }

    fun loginUser(email: String, password: String)
    {
        authLoginInterface.dataValidator(email, password)
    }


}