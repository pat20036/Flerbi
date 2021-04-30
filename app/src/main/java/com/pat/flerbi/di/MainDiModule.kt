package com.pat.flerbi

import com.pat.flerbi.viewmodel.AuthViewModel
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { UserViewModel(get(),get(), get()) }
    single <UserInterface>{ UserInterfaceImpl(androidContext()) }
    single <DatabaseInterface>{ DatabaseInterfaceImpl() }
    single <SharedPreferencesInterface>{ SharedPreferencesInterfaceImpl(androidContext()) }
    single<AuthRegisterInterface> { AuthRegisterInterfaceImpl(androidContext()) }
    single <AuthLoginInterface>{ AuthLoginInterfaceImpl(androidContext()) }
}