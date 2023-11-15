package com.bryan1.mob21firebase.ui.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.bryan1.mob21firebase.core.service.AuthService
import com.bryan1.mob21firebase.core.service.StorageService
import com.bryan1.mob21firebase.data.model.User
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
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val storageService: StorageService,
    private val userRepo: UserRepo
): BaseViewModel() {
 private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown"))
    val user: StateFlow<User> = _user

    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish

    init {
        getCurrentUser()
    }

    fun logout() {
        authService.logout()
        viewModelScope.launch {
            _finish.emit(Unit)
        }
    }

    fun updateProfilePic(uri: Uri) {
        user.value.id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val name = "$it.jpg"
                storageService.addImage(name, uri)
            }
        }
    }

    fun getProfilePicUri() {
        viewModelScope.launch(Dispatchers.IO) {
            authService.getCurrentUser()?.uid?.let {
                _profileUri.value = storageService.getImage("${user.value.id}.jpg")
            }
        }
    }

    fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                safeApiCall { userRepo.getUser(it.uid) }?.let { user ->
                    _user.value = user
                }
            }
        }
    }
}