package com.example.coral

import android.app.Application
import com.example.coral.data.MembersDatabase
import com.example.coral.data.MembersRepository
import com.example.coral.data.MembersRepositoryImpl

class App: Application() {

    private lateinit var database: MembersDatabase
    lateinit var repository: MembersRepository

    override fun onCreate() {

        database = MembersDatabase.getDatabase(this)

        repository = MembersRepositoryImpl(database.membersDao())

        super.onCreate()
    }

}