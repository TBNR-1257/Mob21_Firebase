package com.bryan1.mob21firebase.data.repo

import com.bryan1.mob21firebase.data.model.User

interface UserRepo {

    suspend fun addUser(id: String, user: User)

    suspend fun getUser(id: String): User?

    suspend fun updateUser(id: String, user: User)
}