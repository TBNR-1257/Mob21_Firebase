package com.bryan1.mob21firebase.ui.login

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bryan1.mob21firebase.core.service.AuthService
import com.bryan1.mob21firebase.data.repo.TodosRepo
import com.bryan1.mob21firebase.data.repo.UserRepo
import com.bryan1.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo,
    private val todosRepo: TodosRepo
): BaseViewModel() {
    private val _greet = MutableStateFlow("")
    val greet: StateFlow<String> = _greet

    init {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall { todosRepo.greet() }?.let {
                _greet.value = it
            }
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = safeApiCall { authService.login(email, pass) }

            if(res == null) {
                _error.emit("Email or password is wrong")
            } else {
                _success.emit("Login successful")
            }
        }
    }

}









//@HiltViewModel
//class LoginViewModel @Inject constructor(
//    private val authService: AuthService,
//    private val userRepo: UserRepo
//): BaseViewModel() {
//    private val _finish = MutableSharedFlow<Unit>()
//    val finish: SharedFlow<Unit> = _finish
//
//    fun login (email: String, pass: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val user = safeApiCall {  authService.login(email, pass) }
//
//            if(user == null) {
//                _error.emit("Email or password is wrong")
//            } else {
//                _success.emit("Login successful")
//            }
//
//
//        }
//    }
//
//
//    fun fetchUser(intent: Intent) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val user = authService.getSignInResult(intent)
//            Log.d("debugging", user.toString())
//            user?.let {
//                userRepo.addUser(it.id!!, it)
//                _success.emit("Login Successful")
//            }
//        }
//    }
//
//    suspend fun signInWithGoogle(): IntentSender? {
//        return authService.signInWithGoogle()
//    }
//
//}