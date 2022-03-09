package com.example.coral.viewmodels

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coral.R
import com.example.coral.data.Member
import com.example.coral.data.MembersRepository
import com.example.coral.view.HomeFragment
import com.example.coral.view.member_details.MemberDetailsHolderFragment
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MembersRepository
): ViewModel() {

    var allMembers = MutableLiveData<List<Member>>()
    var dataRow: MutableLiveData<String> = MutableLiveData()
    var historyData: MutableLiveData<MutableList<String>> = MutableLiveData()
    var homeFragment = MutableLiveData<HomeFragment>()

    fun insertMember(member: Member) {
        repository.insert(member)
    }

    fun updateMember(updatedMember: Member) {
        viewModelScope.launch {
            repository.update(updatedMember)
        }
    }

    fun deleteMember(member: Member) {
        viewModelScope.launch {
            repository.delete(member)
        }
    }

    fun getAllMembers() {
        viewModelScope.launch {
            allMembers.postValue(repository.getAllMembers())
        }
    }


}