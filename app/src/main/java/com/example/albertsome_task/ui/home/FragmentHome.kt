package com.example.albertsome_task.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.albertsome_task.adapter.UserPagingDataAdapter
import com.example.albertsome_task.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: UserPagerViewModel by viewModels<UserPagerViewModel>()
    private lateinit var adapter: UserPagingDataAdapter
    private var navigated = false

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
        setListener()
        if(!navigated){
            viewModel.fetchUser(10)
        }
    }

    private fun setupUserList(){
        adapter = UserPagingDataAdapter({user ->
            navigated = true
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentUserDetail(user)
            findNavController().navigate(action)
        })

        binding.rvUser.adapter = adapter
    }

    private fun observeUser(){
        lifecycleScope.launch{
            viewModel.userFlow.collectLatest {pagingData->
                binding.progress.hide()
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setListener(){
        binding.btnAddUser.setOnClickListener{
            showDialog()
        }
    }

    private fun showDialog(){
        InputUserRecordFragment({record ->
            viewModel.fetchUser(record)
        }).show(childFragmentManager,"InputForRecord")
    }
}