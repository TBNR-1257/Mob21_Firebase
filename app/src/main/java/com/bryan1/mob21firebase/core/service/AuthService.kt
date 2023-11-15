package com.bryan1.mob21firebase.core.service

import android.content.Intent
import android.content.IntentSender
import com.bryan1.mob21firebase.R
import com.bryan1.mob21firebase.data.model.User
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class AuthService(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    suspend fun register(email: String, pass: String): FirebaseUser? {
        val task = auth.createUserWithEmailAndPassword(email, pass).await()
        return task.user
    }

    suspend fun login(email: String, pass: String): FirebaseUser? {
        val task = auth.signInWithEmailAndPassword(email, pass).await()
        return task.user
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logout() {
        auth.signOut()
    }
}


//class AuthService(
//    private val signingClient: SignInClient,
//    private val auth: FirebaseAuth = Firebase.auth
//) {
//    suspend fun register(email: String, pass: String): FirebaseUser? {
//        val task = auth.createUserWithEmailAndPassword(email, pass).await()
//        return task.user
//    }
//
//    suspend fun login(email: String, pass: String): FirebaseUser? {
//        val task = auth.createUserWithEmailAndPassword(email, pass).await()
//        return task.user
//    }
//
//    fun logout() {
//        auth.signOut()
//    }
//
//    fun getCurrentUser(): FirebaseUser? {
//        return auth.currentUser
//    }
//
//
//    suspend fun signInWithGoogle(): IntentSender? {
//        val result = try {
//            signingClient.beginSignIn(
//                buildSignInRequest()
//            ).await()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            if(e is CancellationException) throw e
//            null
//        }
//        return result?.pendingIntent?.intentSender
//    }
//
//
//    suspend fun signOut() {
//        try {
//            signingClient.signOut()
//            auth.signOut()
//        } catch (e: Exception) {
//            if(e is CancellationException) throw e
//        }
//    }
//
//
//    fun buildSignInRequest(): BeginSignInRequest {
//        return BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setServerClientId("485305466178-6f5o85h8mb1j761980cgbr7lqp04dmsu.apps.googleusercontent.com")
//                    .setFilterByAuthorizedAccounts(false)
//                    .build()
//            )
//            .build()
//    }
//
//
//    suspend fun getSignInResult(intent: Intent): User? {
//        val credential = SafeParcelableSerializer.deserializeFromIntentExtra(
//            intent,
//            "sign_in_credential",
//            SignInCredential.CREATOR
//        )
//
//        val googleCredential = GoogleAuthProvider.getCredential(credential?.googleIdToken, null)
//
//        return try {
//            val user = auth.signInWithCredential(googleCredential).await().user
//            user?.let {
//                User(
//                    name = it.displayName ?: "",
//                    email = it.email ?: "",
//                    profilePicUrl = it.photoUrl?.toString() ?: "",
//                )
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//}