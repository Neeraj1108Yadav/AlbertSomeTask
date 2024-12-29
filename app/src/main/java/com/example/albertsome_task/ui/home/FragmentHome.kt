package com.example.albertsome_task.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.albertsome_task.R
import com.example.albertsome_task.adapter.UserAdapter
import com.example.albertsome_task.adapter.UserPagingDataAdapter
import com.example.albertsome_task.databinding.FragmentHomeBinding
import com.example.albertsome_task.sealed.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    //private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private val viewModel: UserPagerViewModel by viewModels<UserPagerViewModel>()
    //private lateinit var adapter: UserAdapter
    private lateinit var adapter: UserPagingDataAdapter

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
        //viewModel.getUser()
    }

    private fun setupUserList(){
        adapter = UserPagingDataAdapter({user ->
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentUserDetail(user)
            /*val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragmentHome,false)
                .build()*/
            findNavController().navigate(action)
        })

        binding.rvUser.adapter = adapter
    }

    private fun observeUser(){
        /*viewModel.userData.observe(viewLifecycleOwner){items ->
           when(items){
               is NetworkResult.Success -> {
                   adapter.submitList(items.data.results)
               }
               is NetworkResult.Failure -> {

               }
               is NetworkResult.Loading ->{

               }
           }
        }*/

        lifecycleScope.launch{
            viewModel.flow.collectLatest {pagingData->
                adapter.submitData(pagingData)
            }
        }
    }
}