package com.pat.flerbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pat.flerbi.interfaces.AuthLoginInterface
import com.pat.flerbi.interfaces.AuthRegisterInterface
import com.pat.flerbi.interfaces.AuthSettingsInterface
import com.pat.flerbi.model.LoginError
import com.pat.flerbi.model.RegisterError

class AuthViewModel(
    private val authRegisterInterface: AuthRegisterInterface,
    private val authLoginInterface: AuthLoginInterface,
    private val authSettingsInterface: AuthSettingsInterface
) : ViewModel() {
    private val _loginErrorMessages = MutableLiveData<List<LoginError>>()
    val loginErrorMessages: LiveData<List<LoginError>> get() = _loginErrorMessages

    private val _registerErrorMessages = MutableLiveData<List<RegisterError>>()
    val registerErrorMessages: LiveData<List<RegisterError>> get() = _registerErrorMessages

    private val _isIdentityConfirmed = MutableLiveData<Boolean>()
    val isIdentityConfirmed: LiveData<Boolean> get() = _isIdentityConfirmed

    private val _isPasswordChanged = MutableLiveData<Boolean>()
    val isPasswordChanged: LiveData<Boolean> get() = _isPasswordChanged

    private val _isAccountDeleted = MutableLiveData<Boolean>()
    val isAccountDeleted: LiveData<Boolean> get() = _isAccountDeleted


    fun registerUser(
        email: String,
        password: String,
        rePassword: String,
        nickname: String,
        touCheckBox: Boolean
    ) {
        authRegisterInterface.dataValidator(email, password, rePassword, nickname, touCheckBox)
            .observeForever(Observer {
                _registerErrorMessages.value = it
            })
    }

    fun loginUser(email: String, password: String) {
        authLoginInterface.dataValidator(email, password).observeForever(Observer {
            _loginErrorMessages.value = it
        })
    }

    fun confirmIdentity(email: String, password: String) {
        authSettingsInterface.confirmIdentity(email, password).observeForever(Observer {
            _isIdentityConfirmed.value = it
        })
    }

    fun changePassword(password: String) {
        authSettingsInterface.changePassword(password).observeForever(Observer {
            _isPasswordChanged.value = it
        })
    }

    fun deleteAccount(nickname: String) {
        authSettingsInterface.deleteAccount(nickname).observeForever(Observer {
            _isAccountDeleted.value = it
        })
    }


}