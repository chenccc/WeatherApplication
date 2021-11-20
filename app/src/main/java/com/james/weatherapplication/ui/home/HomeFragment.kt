package com.james.weatherapplication.ui.home

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.james.weatherapplication.R
import com.james.weatherapplication.base.BaseFragment
import com.james.weatherapplication.databinding.FragmentHomeBinding
import com.james.weatherapplication.ext.observe
import com.james.weatherapplication.ui.manager.toBlockActionClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val homeViewModel: HomeViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun getVM(): HomeViewModel = homeViewModel

    override fun bindVM(binding: FragmentHomeBinding, viewModel: HomeViewModel) {
        binding.viewModel = viewModel

        with(viewModel) {
            observe(weatherEvent) { cityWeather ->
                cityWeather.apply {
                    cityName.text = name
                    humidity.text = main.humidity.toString()
                    temp.text = main.temp.toString()
                    currentWeather.text = if (weather.isNotEmpty()) {
                        weather[0].main
                    } else {
                        ""
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun setupView() {
        // block multiple clicks for search button
        search?.setOnClickListener({ _: View? ->
            getVM().clickSearch(cityEditText?.text.toString())
        }.toBlockActionClickListener())
    }
}
