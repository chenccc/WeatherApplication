package com.james.weatherapplication.ui.drawer

import androidx.fragment.app.viewModels
import com.james.weatherapplication.R
import com.james.weatherapplication.base.BaseFragment
import com.james.weatherapplication.databinding.FragmentDrawerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawerFragment: BaseFragment<FragmentDrawerBinding, DrawerViewModel>() {
    private val drawerViewModel: DrawerViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_drawer

    override fun getVM(): DrawerViewModel = drawerViewModel

    override fun bindVM(binding: FragmentDrawerBinding, viewModel: DrawerViewModel) = Unit

    companion object {
        fun newInstance() = DrawerFragment()
    }

    override fun setupView() {
    }
}