package com.james.weatherapplication.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.james.weatherapplication.db.AppDB
import com.james.weatherapplication.utils.CityWeatherHelper
import com.james.weatherapplication.utils.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CityWeatherDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named(Constants.TEST_DB)
    lateinit var database: AppDB

    private lateinit var cityWeatherDao: CityWeatherDao

    @Before
    fun setup() {
        hiltRule.inject()
        cityWeatherDao = database.cityWeatherDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertWeather() = runBlockingTest {
        val cityWeather = CityWeatherHelper.createEmptyCityWeather().copy(
            name = "test1"
        )
        cityWeatherDao.insertWeather(cityWeather)
        val cityWeatherList = cityWeatherDao.getAllRequestCities()

        assertThat(cityWeatherList).contains(cityWeather)
    }

    @Test
    fun clearCity() = runBlockingTest {
        val cityWeather = CityWeatherHelper.createEmptyCityWeather().copy(
            name = "test1"
        )
        cityWeatherDao.insertWeather(cityWeather)
        cityWeatherDao.clearCity(cityWeather.name)

        val cityWeatherList = cityWeatherDao.getAllRequestCities()
        assertThat(cityWeatherList).isEmpty()
    }

    @Test
    fun getAllCitiesInDescOrderForLastAccessTime() = runBlockingTest {
        val firstCityWeather = CityWeatherHelper.createEmptyCityWeather().copy(
            name = "test1",
            lastAccessTime = 1
        )

        val secondCityWeather = CityWeatherHelper.createEmptyCityWeather().copy(
            name = "test2",
            lastAccessTime = 2
        )

        cityWeatherDao.insertWeather(firstCityWeather)
        cityWeatherDao.insertWeather(secondCityWeather)

        val cityWeatherList = cityWeatherDao.getAllRequestCities()

        assertThat(cityWeatherList.size).isEqualTo(2)
        assertThat(cityWeatherList[0].name).isEqualTo("test2")
        assertThat(cityWeatherList[1].name).isEqualTo("test1")
    }

    @Test
    fun getWeatherForCity() = runBlockingTest {
        val firstCityWeather = CityWeatherHelper.createEmptyCityWeather().copy(
            name = "test1",
        )

        val secondCityWeather = CityWeatherHelper.createEmptyCityWeather().copy(
            name = "test2",
        )

        cityWeatherDao.insertWeather(firstCityWeather)
        cityWeatherDao.insertWeather(secondCityWeather)

        val result = cityWeatherDao.getWeatherForCity(firstCityWeather.name.uppercase())
        assertThat(result).isEqualTo(firstCityWeather)
    }
}