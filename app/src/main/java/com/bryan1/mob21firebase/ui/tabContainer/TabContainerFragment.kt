package com.bryan1.mob21firebase.ui.tabContainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bryan1.mob21firebase.R
import com.bryan1.mob21firebase.databinding.FragmentTabContainerBinding
import com.bryan1.mob21firebase.ui.adapter.FragmentAdapter
import com.bryan1.mob21firebase.ui.home.HomeFragment
import com.bryan1.mob21firebase.ui.profile.ProfileFragment
import com.google.android.material.tabs.TabLayoutMediator


class TabContainerFragment : Fragment() {
    private lateinit var binding: FragmentTabContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTabContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpContainer.adapter = FragmentAdapter(
            this,
            listOf(HomeFragment(), ProfileFragment())
        )

        TabLayoutMediator(binding.tlTabs, binding.vpContainer) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Home"
                }
                else -> {
                    tab.text = "Profile"
                }
            }
        }.attach()
    }

}