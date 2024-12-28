package com.example.albertsome_task.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.albertsome_task.R
import com.example.albertsome_task.adapter.UserAdapter
import com.example.albertsome_task.databinding.FragmentHomeBinding
import com.example.albertsome_task.sealed.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUserList()
        observeUser()
        viewModel.getUser()
    }

    private fun setupUserList(){
        adapter = UserAdapter()
        binding.rvUser.adapter = adapter
    }

    private fun observeUser(){
        viewModel.userData.observe(viewLifecycleOwner){items ->
           when(items){
               is NetworkResult.Success -> {
                   adapter.submitList(items.data.results)
               }
               is NetworkResult.Failure -> {

               }
               is NetworkResult.Loading ->{

               }
           }
        }
    }
}