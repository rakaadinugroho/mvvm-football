package xyz.rakalabs.englishfootball.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.rakalabs.englishfootball.BuildConfig


object FootballApiClient {
    fun provideApi(): FootballApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballApiService::class.java)
    }
}