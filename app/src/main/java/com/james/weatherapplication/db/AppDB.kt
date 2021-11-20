package com.james.weatherapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.james.weatherapplication.data.model.CityWeather
import com.james.weatherapplication.db.dao.CityWeatherDao
import com.james.weatherapplication.utils.WeatherListConverter

@Database(entities = [CityWeather::class], version = 1, exportSchema = false)
@TypeConverters(WeatherListConverter::class)
abstract class AppDB: RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao

    companion object {
        @Volatile private var instance: AppDB? = null
        private const val DB_NAME = "cityWeatherDB"

        fun getDatabase(context: Context, inMemoryUse: Boolean = false): AppDB =
            instance ?: synchronized(this) { instance ?:
            buildDatabase(context, inMemoryUse).also { instance = it } }

        private fun buildDatabase(appContext: Context, inMemoryUse: Boolean) =
            if (inMemoryUse) {
                Room.inMemoryDatabaseBuilder(appContext, AppDB::class.java)
                    .fallbackToDestructiveMigration()
                    .build()
            } else {
                Room.databaseBuilder(appContext, AppDB::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}