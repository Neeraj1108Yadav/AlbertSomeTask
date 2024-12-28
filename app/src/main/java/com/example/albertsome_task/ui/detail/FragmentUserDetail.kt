package com.example.albertsome_task.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.albertsome_task.R
import com.example.albertsome_task.databinding.FragmentUserDetailBinding
import com.example.albertsome_task.model.User

class FragmentUserDetail : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModels<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val args = FragmentUserDetailArgs.fromBundle(requireArguments())
        viewModel.setUser(args.user)
        return binding.root
    }

}