package com.example.studymytaskapp.appinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.studymytaskapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppInfoFragment : Fragment() {

    companion object {
        fun newInstance() = AppInfoFragment()
    }

    private val viewModel: AppInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.app_info_fragment, container, false)
    }

}