package com.bryan1.mob21firebase.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bryan1.mob21firebase.R
import com.bryan1.mob21firebase.databinding.FragmentProfileBinding
import com.bryan1.mob21firebase.ui.base.BaseFragment
import com.bryan1.mob21firebase.ui.tabContainer.TabContainerFragmentDirections
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel: ProfileViewModel by viewModels()
    lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                viewModel.updateProfilePic(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.finish.collect {
                val action = TabContainerFragmentDirections.toLogin()
                navController.navigate(action)
            }
        }

        lifecycleScope.launch {
            viewModel.user.collect {
                binding.tvEmail.text = it.email
                binding.tvName.text = it.name
            }
        }


        lifecycleScope.launch {
            viewModel.user.collect {
               Glide.with(requireContext())
                   .load(it)
                   .placeholder(R.drawable.ic_person)
                   .into(binding.ivProfile)
            }
        }
    }

}