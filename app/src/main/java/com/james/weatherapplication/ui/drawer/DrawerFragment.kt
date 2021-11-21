package com.james.weatherapplication.ui.drawer

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.james.weatherapplication.R
import com.james.weatherapplication.base.BaseFragment
import com.james.weatherapplication.databinding.FragmentDrawerBinding
import com.james.weatherapplication.ext.observe
import com.james.weatherapplication.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawerFragment: BaseFragment<FragmentDrawerBinding, DrawerViewModel>() {
    private val drawerViewModel: DrawerViewModel by viewModels()

    // get shared viewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override val layoutId: Int
        get() = R.layout.fragment_drawer

    override fun getVM(): DrawerViewModel = drawerViewModel

    override fun bindVM(binding: FragmentDrawerBinding, viewModel: DrawerViewModel) = Unit

    companion object {
        fun newInstance() = DrawerFragment()
        private const val TAG = "DrawerFragment"
    }

    override fun setupView() {
        with(mainViewModel) {
            observe(drawerOpenEvent) {
                drawerViewModel.getCities()
            }
        }
    }
}