package com.ilagoproject.myapplication.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ilagoproject.myapplication.App
import com.ilagoproject.myapplication.R
import com.ilagoproject.myapplication.databinding.FragmentProfileBinding
import com.ilagoproject.myapplication.viewmodel.base.AccountViewModelFragment


class ProfileFragment : AccountViewModelFragment() {

    init {
        App.modelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = this
        return binding.root
    }
}