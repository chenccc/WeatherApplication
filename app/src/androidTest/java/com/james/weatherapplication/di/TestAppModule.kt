package com.james.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.james.weatherapplication.db.AppDB
import com.james.weatherapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {
    @Provides
    @Named(Constants.TEST_DB)
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDB::class.java)
            .allowMainThreadQueries().build()
}