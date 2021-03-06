package com.pat.flerbi.di

import com.pat.flerbi.interfaces.*
import com.pat.flerbi.viewmodel.AuthViewModel
import com.pat.flerbi.viewmodel.ChatViewModel
import com.pat.flerbi.viewmodel.QueueViewModel
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { UserViewModel(get(), get(), get()) }
    viewModel { ChatViewModel(get()) }
    viewModel { QueueViewModel(get()) }
    single<QueueInterface> { QueueInterfaceImpl(androidContext()) }
    single<DatabaseChatRepositoryInterface> { DatabaseChatRepositoryInterfaceImpl() }
    single<UserInterface> { UserInterfaceImpl(androidContext()) }
    single<OnlineOfflineInterface> { OnlineOfflineInterfaceImpl() }
    single<SharedPreferencesInterface> { SharedPreferencesInterfaceImpl(androidContext()) }
    single<AuthRegisterInterface> { AuthRegisterInterfaceImpl(androidContext()) }
    single<AuthLoginInterface> { AuthLoginInterfaceImpl(androidContext()) }
    single<AuthSettingsInterface> { AuthSettingsInterfaceImpl(androidContext()) }
}