package com.bryan1.mob21firebase.data.repo

import com.bryan1.mob21firebase.data.model.User
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class UserRepoImpl(
    private val dbRef: CollectionReference
): UserRepo {
    override suspend fun addUser(id: String, user: User) {
        dbRef.document(id).set(user.toHashMap()).await()
    }

    override suspend fun getUser(id: String): User? {
        val snapshot = dbRef.document(id).get().await()
        return snapshot.data?.let {
            it["id"] = snapshot.id
            User.fromHashMap(it)
        }
    }

    override suspend fun updateUser(id: String, user: User) {
        dbRef.document(id).set(user.toHashMap()).await()
    }
}