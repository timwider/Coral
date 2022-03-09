package com.example.coral.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coral.data.MembersRepository

class MainViewModelFactory(
    private val repository: MembersRepository,
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        return MainViewModel(repository) as T
    }
}