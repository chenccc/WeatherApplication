package com.james.weatherapplication.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.james.weatherapplication.data.service.WeatherApiService
import com.james.weatherapplication.db.AppDB
import com.james.weatherapplication.utils.CityWeatherHelper
import com.google.common.truth.Truth.assertThat
import com.james.weatherapplication.utils.NetworkUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@SmallTest
class WeatherRepositoryImplTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherRepository: WeatherRepository

    @RelaxedMockK
    private lateinit var database: AppDB

    @RelaxedMockK
    private lateinit var weatherApiService: WeatherApiService

    private val stubWeather = CityWeatherHelper.createEmptyCityWeather()
    private val apiWeather = stubWeather.copy(name = "api")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkObject(NetworkUtils)
        weatherRepository = WeatherRepositoryImpl(
            db = database,
            service = weatherApiService,
            appContext = ApplicationProvider.getApplicationContext()
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getWeatherForCity_CityNotInDB_hasNetwork_cannotFindCityFromApi_throwException() = runBlockingTest {
        coEvery { database.cityWeatherDao().getWeatherForCity(any()) } returns null
        every { NetworkUtils.isNetworkConnected(any()) } returns true
        coEvery { weatherApiService.getWeatherForCity(any(), any()) }.throws(RuntimeException())

        var exceptionThrown = false

        try {
            weatherRepository.getWeatherForCity("Hong Kong")
        } catch (exception: RuntimeException) {
            exceptionThrown = true
        }

        assertThat(exceptionThrown).isTrue()
    }

    @Test
    fun getWeatherForCity_CityNotInDB_NoNetwork_throwException() = runBlockingTest {
        coEvery { database.cityWeatherDao().getWeatherForCity(any()) } returns null
        every { NetworkUtils.isNetworkConnected(any()) } returns false
        coEvery { weatherApiService.getWeatherForCity(any(), any()) }.throws(RuntimeException())

        var exceptionThrown = false

        try {
            weatherRepository.getWeatherForCity("Hong Kong")
        } catch (exception: RuntimeException) {
            exceptionThrown = true
        }

        assertThat(exceptionThrown).isTrue()
    }
    @Test
    fun getWeatherForCity_CityNotInDB_HasNetwork_canFindCityFromApi_returnWeatherFromAPI() = runBlockingTest {
        coEvery { database.cityWeatherDao().getWeatherForCity(any()) } returns null
        every { NetworkUtils.isNetworkConnected(any()) } returns true
        coEvery { weatherApiService.getWeatherForCity(any(), any()) }  returns apiWeather
        val result = weatherRepository.getWeatherForCity("Hong Kong")
        assertThat(result).isEqualTo(apiWeather)
    }

    @Test
    fun getWeatherForCity_CityInDB_noNetwork_returnWeatherFromDB() = runBlockingTest {
        coEvery { database.cityWeatherDao().getWeatherForCity(any()) } returns stubWeather
        every { NetworkUtils.isNetworkConnected(any()) } returns false
        val result = weatherRepository.getWeatherForCity("Hong Kong")
        assertThat(result).isEqualTo(stubWeather)
    }

    @Test
    fun getWeatherForCity_CityInDB_HasNetwork_canFindCityFromApi_returnWeatherFromAPI() = runBlockingTest {
        coEvery { database.cityWeatherDao().getWeatherForCity(any()) } returns stubWeather
        every { NetworkUtils.isNetworkConnected(any()) } returns true
        coEvery { weatherApiService.getWeatherForCity(any(), any()) }  returns apiWeather
        val result = weatherRepository.getWeatherForCity("Hong Kong")
        assertThat(result).isEqualTo(apiWeather)
    }
}