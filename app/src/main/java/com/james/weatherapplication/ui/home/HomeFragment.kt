package com.james.weatherapplication.ui.home

import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.james.weatherapplication.R
import com.james.weatherapplication.base.BaseFragment
import com.james.weatherapplication.data.model.CityWeather
import com.james.weatherapplication.databinding.FragmentHomeBinding
import com.james.weatherapplication.ext.observe
import com.james.weatherapplication.ui.MainViewModel
import com.james.weatherapplication.ui.manager.toBlockActionClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun getVM(): HomeViewModel = homeViewModel

    override fun bindVM(binding: FragmentHomeBinding, viewModel: HomeViewModel) {
        binding.viewModel = viewModel

        with(viewModel) {
            observe(weatherEvent) {
                fillHomePage(it)
            }
        }

        with(mainViewModel) {
            observe(changeCityEvent) {
                viewModel.clickSearch(it)
            }
        }
    }

    private fun fillHomePage(cityWeather: CityWeather) {
        cityWeather.apply {
            cityName.text = name
            humidity.text = main.humidity.toString()
            country.text = sys.country
            temp.text = main.temp.toString()
            pressure.text = main.pressure.toString()
            currentWeather.text = if (weather.isNotEmpty()) {
                weather[0].main
            } else {
                ""
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
