package com.pat.flerbi.viewmodel

import android.widget.CheckBox
import androidx.lifecycle.ViewModel
import com.pat.flerbi.AuthRegisterInterface

class AuthViewModel(private val authRegisterInterface: AuthRegisterInterface): ViewModel() {
    fun registerUser(email:String, password:String, rePassword: String, nickname: String, touCheckBox: Boolean)
    {
        authRegisterInterface.dataValidator(email, password, rePassword, nickname, touCheckBox)
    }
}