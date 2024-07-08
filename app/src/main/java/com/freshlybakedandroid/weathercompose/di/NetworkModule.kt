package com.freshlybakedandroid.weathercompose.di

import com.freshlybakedandroid.weathercompose.network.WeatherAPI
import com.freshlybakedandroid.weathercompose.utill.Constants
import com.freshlybakedandroid.weathercompose.weather.WeatherRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit {

        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun providesWeatherAPI(retrofit: Retrofit): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepository(weatherAPI)
    }

}