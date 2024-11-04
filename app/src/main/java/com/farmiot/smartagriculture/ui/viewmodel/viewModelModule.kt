package com.farmiot.smartagriculture.ui.viewmodel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

//    single { MainViewModel() }
    viewModel { SigninRegisterViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
}