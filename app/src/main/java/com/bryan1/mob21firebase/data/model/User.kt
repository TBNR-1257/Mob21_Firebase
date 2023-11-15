package com.bryan1.mob21firebase.data.model

data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val profilePicUrl: String = ""
) {
    fun toHashMap(): HashMap<String, String?> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "profilePicUrl" to profilePicUrl
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any>): User {
            return User(
                name = hash["name"].toString(),
                email = hash["email"].toString(),
                profilePicUrl = hash["profilePicUrl"].toString(),
            )
        }
    }
}
