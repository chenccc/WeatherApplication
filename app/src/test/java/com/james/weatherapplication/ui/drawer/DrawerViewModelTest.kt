package com.james.weatherapplication.ui.drawer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.james.weatherapplication.MainCoroutineRule
import com.james.weatherapplication.data.model.CityWeather
import com.james.weatherapplication.data.repository.WeatherRepository
import com.james.weatherapplication.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.james.weatherapplication.utils.CityWeatherHelper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class DrawerViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var drawerViewModel: DrawerViewModel

    private val stubCityWeather = CityWeatherHelper.createEmptyCityWeather().copy(
        name = "ShangHai"
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
        drawerViewModel = DrawerViewModel(weatherRepository)
    }

    @Test
    fun `get cities, repository returns city list, city list in cityWeatherList`() = runBlockingTest {
        coEvery { weatherRepository.getAllCities() } returns mockedCityWeatherList
        drawerViewModel.getCities()
        val result = drawerViewModel.cityWeatherList.getOrAwaitValue()
        assertThat(result).isEqualTo(mockedCityWeatherList)
    }

    @Test
    fun `get cities, repository getAllCities() throws exception, exception message in errorMessage`() = runBlockingTest {
        coEvery { weatherRepository.getAllCities() } throws RuntimeException("Network error")
        drawerViewModel.getCities()
        val message = drawerViewModel.errorMessage.getOrAwaitValue()
        assertThat(message).isEqualTo("Network error")
    }

    @Test
    fun `select city, city name in selectWeather`() {
        drawerViewModel.selectCity(stubCityWeather)
        assertThat(drawerViewModel.selectWeather.value).isEqualTo(stubCityWeather.name)
    }

    @Test
    fun `delete city, deleteCity in repository throws exception, exception message in errorMessage`() = runBlockingTest {
        coEvery { weatherRepository.deleteCity(any()) } throws RuntimeException("Cannot find city in DB")
        drawerViewModel.deleteCity("ShangHai")
        val message = drawerViewModel.errorMessage.getOrAwaitValue()
        assertThat(message).isEqualTo("Cannot find city in DB")
    }
}