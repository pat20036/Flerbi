package com.pat.flerbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pat.flerbi.interfaces.AuthLoginInterface
import com.pat.flerbi.interfaces.AuthRegisterInterface
import com.pat.flerbi.model.LoginError
import com.pat.flerbi.model.RegisterError

class AuthViewModel(private val authRegisterInterface: AuthRegisterInterface, private val authLoginInterface: AuthLoginInterface): ViewModel() {
    private val _loginErrorMessages = MutableLiveData<List<LoginError>>()
    val loginErrorMessages:LiveData<List<LoginError>> get() = _loginErrorMessages

    private val _registerErrorMessages = MutableLiveData<List<RegisterError>>()
    val registerErrorMessages:LiveData<List<RegisterError>> get() = _registerErrorMessages

    fun registerUser(email:String, password:String, rePassword: String, nickname: String, touCheckBox: Boolean)
    {
        authRegisterInterface.dataValidator(email, password, rePassword, nickname, touCheckBox).observeForever(Observer {
                _registerErrorMessages.value = it
            })
    }

    fun loginUser(email: String, password: String)
    {
        authLoginInterface.dataValidator(email, password).observeForever(Observer {
            _loginErrorMessages.value = it
        })
    }




}