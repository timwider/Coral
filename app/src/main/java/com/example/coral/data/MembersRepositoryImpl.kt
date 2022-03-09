package com.example.coral.data

import androidx.lifecycle.LiveData

class MembersRepositoryImpl(
    private val dao: MembersDao
): MembersRepository {
    override fun insert(member: Member) = dao.insert(member)

    override suspend fun update(member: Member) = dao.update(member)

    override suspend fun delete(member: Member) = dao.delete(member)

    override fun getAllMembers(): List<Member> {
        return dao.getAllMembers()
    }
}