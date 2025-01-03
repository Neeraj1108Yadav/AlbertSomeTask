package com.example.albertsome_task.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.flatMap
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsome_task.adapter.UserPagingDataAdapter
import com.example.albertsome_task.databinding.FragmentHomeBinding
import com.example.albertsome_task.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        adapter = UserPagingDataAdapter(
            { user ->
                navigated = true
                val action = FragmentHomeDirections.actionFragmentHomeToFragmentUserDetail(user)
                findNavController().navigate(action)
            },
            {enableSearchView ->
                if (enableSearchView) {
                    binding.edSearchView.visibility = View.VISIBLE
                }
            }
        )

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

        binding.btnSortAge.setOnClickListener{

        }

        binding.edSearchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                s?.let{
                    if(it.toString().isNotEmpty()){
                        viewModel.updateSearchQuery(it.toString())
                    }
                }
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun showDialog(){
        InputUserRecordFragment({record ->
            viewModel.fetchUser(record)
        }).show(childFragmentManager,"InputForRecord")
    }

}