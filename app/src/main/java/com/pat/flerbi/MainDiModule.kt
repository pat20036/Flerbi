package com.pat.flerbi

import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel { AuthViewModel(get(), get()) }
    single<AuthRegisterInterface> { AuthRegisterInterfaceImpl(androidContext()) }
    single <AuthLoginInterface>{ AuthLoginInterfaceImpl(androidContext()) }
}