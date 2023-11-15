package com.bryan1.mob21firebase.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bryan1.mob21firebase.R
import com.bryan1.mob21firebase.core.utils.NativeUtils
import com.bryan1.mob21firebase.data.model.Todo
//import com.bryan1.mob21firebase.data.apis.TodosApi
import com.bryan1.mob21firebase.databinding.FragmentHomeBinding
import com.bryan1.mob21firebase.ui.adapter.TodosAdapter
import com.bryan1.mob21firebase.ui.base.BaseFragment
import com.bryan1.mob21firebase.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: TodosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun setupUIComponents() {
        super.setupUIComponents()
        setupAdapter()

        binding.fabAdd.setOnClickListener {
            val action = TabContainerFragmentDirections.actionHomeToAddTodo()
            navController.navigate(action)
        }

        binding.tvNativeMsg.text = NativeUtils.greet()
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.todos.collect {
                adapter.setTodos(it)
            }
        }
    }

    private fun setupAdapter() {
        adapter = TodosAdapter(emptyList())
        adapter.listener = object: TodosAdapter.Listener {
            override fun onClick(todo: Todo) {
                val action = TabContainerFragmentDirections.actionHomeToEditTodo(todo.id)
                navController.navigate(action)
            }

            override fun onDelete(todo: Todo) {
                viewModel.delete(todo)
            }
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodos.adapter = adapter
        binding.rvTodos.layoutManager = layoutManager
    }

}