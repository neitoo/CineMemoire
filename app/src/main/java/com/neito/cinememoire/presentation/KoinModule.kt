package com.neito.cinememoire.presentation

import com.neito.cinememoire.data.remote.ApiService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiService.create() }
    viewModel { SearchViewModel(get())}
}