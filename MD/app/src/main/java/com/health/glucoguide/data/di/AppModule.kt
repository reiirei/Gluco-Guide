package com.health.glucoguide.data.di

import android.content.Context
import androidx.room.Room
import com.health.glucoguide.BuildConfig
import com.health.glucoguide.data.local.GlucoDatabase
import com.health.glucoguide.data.local.HistoryDao
import com.health.glucoguide.data.remote.ApiService
import com.health.glucoguide.util.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGlucoDatabase(@ApplicationContext context: Context): GlucoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GlucoDatabase::class.java,
            "gluco_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHistoryDao(database: GlucoDatabase): HistoryDao {
        return database.historyDao()
    }
}
