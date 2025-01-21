package com.example.testwithpoetry.data

import com.example.testwithpoetry.data.room.UserDao
import com.example.testwithpoetry.data.room.UserEntity
import com.example.testwithpoetry.domain.UserRepository
import com.example.testwithpoetry.domain.localModels.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUser(): User? {
        val entity = userDao.getUser()
        return entity?.let { User(it.id, it.name, it.email, it.birthday) }
    }

    override suspend fun insertUser(user: User) {
        val entity = UserEntity(user.id, user.name, user.email, user.birthday)
        userDao.insertUser(entity)
    }
}