package com.james.weatherapplication.di

import com.james.weatherapplication.StubInterceptor
import android.content.Context
import com.james.weatherapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SampleDataModule {
    @Provides
    @Singleton
    @Named(Constants.STUB_INTERCEPTOR_KEY)
    @JvmStatic
    fun provideStubInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        return StubInterceptor()
    }
}