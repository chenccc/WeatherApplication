package com.james.weatherapplication.ui.drawer

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.james.weatherapplication.R
import com.james.weatherapplication.base.BaseFragment
import com.james.weatherapplication.databinding.FragmentDrawerBinding
import com.james.weatherapplication.ext.observe
import com.james.weatherapplication.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_drawer.*

@AndroidEntryPoint
class DrawerFragment: BaseFragment<FragmentDrawerBinding, DrawerViewModel>() {
    private val drawerViewModel: DrawerViewModel by viewModels()

    // get shared viewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: CityListAdapter

    override val layoutId: Int
        get() = R.layout.fragment_drawer

    override fun getVM(): DrawerViewModel = drawerViewModel

    override fun bindVM(binding: FragmentDrawerBinding, viewModel: DrawerViewModel) {
        binding.viewModel = viewModel
    }

    companion object {
        fun newInstance() = DrawerFragment()
        private const val TAG = "DrawerFragment"
    }

    override fun setupView() {
        adapter = CityListAdapter(viewModel = drawerViewModel)
        cityRecyclerView?.adapter = adapter
        cityRecyclerView?.layoutManager = LinearLayoutManager(context)

        with(mainViewModel) {
            observe(drawerOpenEvent) {
                drawerViewModel.getCities()
            }
        }

        with(drawerViewModel) {
            getCities()
            observe(cityWeatherList) {
                adapter.submitList(it)
            }

            observe(selectWeather) {
                mainViewModel.apply {
                    changeCityEvent.postValue(it)
                    closeDrawerEvent.postValue(Unit)
                }
            }
        }
    }
}