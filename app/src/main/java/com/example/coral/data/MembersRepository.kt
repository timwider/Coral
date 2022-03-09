package com.example.coral.data

interface MembersRepository {

    fun insert(member: Member)

    suspend fun update(member: Member)

    suspend fun delete(member: Member)

    fun getAllMembers(): List<Member>
}