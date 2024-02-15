package com.android.movieapp.data.di

import com.android.movieapp.viewmodels.LocalDataViewModel
import dagger.Component

@Component(modules = [MainModule::class])
interface AppComponent {

    fun inject(viewModel: LocalDataViewModel)
}