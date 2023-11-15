package com.bryan1.mob21firebase.ui.login

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bryan1.mob21firebase.R
import com.bryan1.mob21firebase.databinding.FragmentLoginBinding
import com.bryan1.mob21firebase.ui.base.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()
    private lateinit var signInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("debugging", "Result code:${result.resultCode}")
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val acc = task.result!!
                Log.d("debugging", acc.displayName.toString())
            } catch (e:Exception) {
                e.printStackTrace()
                Log.d("debugging", e.message.toString())
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.oauth_client))
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.run {
            tvRegister.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginToRegister()
                navController.navigate(action)
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val pass = etPassword.text.toString()
                viewModel.login(email, pass)
            }

            btnSignInWithGoogle.setOnClickListener{
                googleSignInLauncher.launch(signInClient.signInIntent)
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.success.collect {
                val action = LoginFragmentDirections.toHome()
                navController.navigate(action)
            }
        }


        lifecycleScope.launch {
            viewModel.greet.collect {
                binding.tvGreet.text = it
            }
        }

        // Creates a login fragment
        // Initialize the Google Sign-In launcher
    }
    // when the google sign in finishes, lambda function is executed and authenticates with firebase

}








//@AndroidEntryPoint
//class LoginFragment : BaseFragment<FragmentLoginBinding>() {
//    override val viewModel: LoginViewModel by viewModels()
//    private lateinit var signInClient: GoogleSignInClient
//
//    private val googleSignInLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        Log.d("debugging","Result Code: ${result.resultCode}")
//        if (result.resultCode == RESULT_OK) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            try {
////                val account = task.getResult(ApiException::class.java)!!
////                firebaseAuthWithGoogle(account.idToken!!)
//
//                val acc = task.result!!
//                Log.d("debugging", acc.displayName.toString())
//            } catch (e: ApiException) {
//                e.printStackTrace()
//                Log.d("debugging", e.message.toString())
////                Log.w("TAG", "Google sign in failed", e)
//            }
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun setupUIComponents() {
//        super.setupUIComponents()
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            // specified that an id token should be requested
//            .requestIdToken(getString(R.string.oauth_client))
//            // requests users email
//            .requestEmail()
//            // builds the functions with all the specified objects
//            .build()
//        // initiates the google sign in process and retrieve the resulting authentication up above
//        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//        binding.btnSignInWithGoogle.setOnClickListener {
//            val signInIntent = mGoogleSignInClient.signInIntent
//            Log.d("testing","$signInIntent")
//            googleSignInLauncher.launch(signInIntent)
//        }
//
//
//        binding.run {
//            tvRegister.setOnClickListener {
//                val action = LoginFragmentDirections.actionLoginToRegister()
//                navController.navigate(action)
//            }
//
//            btnLogin.setOnClickListener {
//                val email = etEmail.text.toString()
//                val pass = etPassword.text.toString()
//                viewModel.login(email, pass)
//            }
//        }
//    }
//
//    override fun setupViewModelObserver() {
//        super.setupViewModelObserver()
//
//        lifecycleScope.launch {
//            viewModel.success.collect {
//                val action = LoginFragmentDirections.toHome()
//                navController.navigate(action)
//            }
//        }
//        // Creates a login fragment
//        // Initialize the Google Sign-In launcher
//    }
//    // when the google sign in finishes, lambda function is executed and authenticates with firebase
//
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        val firebaseAuth = FirebaseAuth.getInstance()
//
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener(requireActivity()) { task ->
//                // toasts if the sign in is successful
//                if (task.isSuccessful) {
//                    val action = LoginFragmentDirections.toHome()
//                    navController.navigate(action)
//                    // toasts if the sign in is failed
//                }
//            }
//    }
//
//}













