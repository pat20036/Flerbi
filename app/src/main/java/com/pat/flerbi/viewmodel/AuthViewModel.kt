package com.pat.flerbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pat.flerbi.interfaces.AuthLoginInterface
import com.pat.flerbi.AuthRegisterInterface
import com.pat.flerbi.model.LoginError

class AuthViewModel(private val authRegisterInterface: AuthRegisterInterface, private val authLoginInterface: AuthLoginInterface): ViewModel() {
    private val _loginErrorMessages = MutableLiveData<List<LoginError>>()
    val loginErrorMessages:LiveData<List<LoginError>> get() = _loginErrorMessages

    fun registerUser(email:String, password:String, rePassword: String, nickname: String, touCheckBox: Boolean)
    {
        authRegisterInterface.dataValidator(email, password, rePassword, nickname, touCheckBox)
    }

    fun loginUser(email: String, password: String)
    {
        authLoginInterface.dataValidator(email, password).observeForever(Observer {
            _loginErrorMessages.value = it
        })
    }




}