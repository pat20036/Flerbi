package com.pat.flerbi

import com.pat.flerbi.interfaces.DatabaseChatRepositoryInterface
import com.pat.flerbi.interfaces.DatabaseChatRepositoryInterfaceImpl
import com.pat.flerbi.viewmodel.AuthViewModel
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { UserViewModel(get(),get(), get()) }
    viewModel { ChatViewModel(get()) }
    single <DatabaseChatRepositoryInterface>{ DatabaseChatRepositoryInterfaceImpl() }
    single <UserInterface>{ UserInterfaceImpl(androidContext()) }
    single <OnlineOfflineInterface>{ OnlineOfflineInterfaceImpl() }
    single <SharedPreferencesInterface>{ SharedPreferencesInterfaceImpl(androidContext()) }
    single<AuthRegisterInterface> { AuthRegisterInterfaceImpl(androidContext()) }
    single <AuthLoginInterface>{ AuthLoginInterfaceImpl(androidContext()) }
}