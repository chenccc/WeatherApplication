package com.james.weatherapplication.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.james.weatherapplication.data.repository.WeatherRepository
import com.james.weatherapplication.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.james.weatherapplication.MainCoroutineRule
import com.james.weatherapplication.data.model.CityWeather
import com.james.weatherapplication.utils.CityWeatherHelper
import com.james.weatherapplication.utils.Constants
import io.mockk.verify
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var homeViewModel: HomeViewModel

    private val stubCityWeather = CityWeatherHelper.createEmptyCityWeather().copy(
        name = "Shanghai"
    )

    private val mockedCityWeatherList = listOf<CityWeather>(
        CityWeatherHelper.createEmptyCityWeather().copy(
            name = "BeiJing",
            lastAccessTime = 3
        ),
        CityWeatherHelper.createEmptyCityWeather().copy(
            name = "HongKong",
            lastAccessTime = 2
        ),
        CityWeatherHelper.createEmptyCityWeather().copy(
            name = "Tokyo",
            lastAccessTime = 1
        )
    )

    @Before
    fun setUp() {
        weatherRepository = mockk()
        homeViewModel = HomeViewModel(weatherRepository)
    }

    @Test
    fun `get last access city, have cities in repository, call clickSearch for first element of the list` ()  = runBlockingTest {
        coEvery { weatherRepository.getAllCities() } returns mockedCityWeatherList
        homeViewModel.getLastAccessCity()
        verify {
            homeViewModel.clickSearch(mockedCityWeatherList[0].name)
        }
    }

    @Test
    fun `get last access city, getAllCities in repository throws exception, put exception message into error message` ()  = runBlockingTest {
        coEvery { weatherRepository.getAllCities() } throws RuntimeException("Network problem")
        homeViewModel.getLastAccessCity()
        val message = homeViewModel.errorMessage.getOrAwaitValue()
        assertThat(message).isEqualTo("Network problem")
    }

    @Test
    fun `click search, cityName is empty, put cannot search for empty city name into error message`() = runBlockingTest {
        homeViewModel.clickSearch("")
        val message = homeViewModel.errorMessage.getOrAwaitValue()
        assertThat(message).isEqualTo(Constants.CANNOT_SEARCH_FOR_EMPTY_NAME)
    }

    @Test
    fun `click search, cityName is not empty, getWeatherForCity of repository throws exception, put exception message into error message`() = runBlockingTest {
        coEvery { weatherRepository.getWeatherForCity(any()) } throws RuntimeException("Network problem")
        homeViewModel.clickSearch("BeiJing")
        val message = homeViewModel.errorMessage.getOrAwaitValue()
        assertThat(message).isEqualTo("Network problem")
    }

    @Test
    fun `click search, cityName is not empty, getWeatherForCity of repository returns cityWeather, put cityWeather into weatherField`() = runBlockingTest {
        coEvery { weatherRepository.getWeatherForCity(any()) } returns stubCityWeather
        homeViewModel.clickSearch("BeiJing")
        assertThat(homeViewModel.weatherField.get()).isEqualTo(stubCityWeather)
    }
}
