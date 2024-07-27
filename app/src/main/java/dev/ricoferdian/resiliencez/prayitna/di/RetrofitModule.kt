package dev.ricoferdian.resiliencez.prayitna.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ricoferdian.resiliencez.prayitna.data.remote.OsmApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @OSM
    fun provideOsmRetrofit(
        @Named("OSM") baseUrl: String
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Named("OSM")
    fun provideBaseUrlOsm(): String {
        return "https://nominatim.openstreetmap.org/"
    }

    @Provides
    fun provideOsmService(@OSM retrofit: Retrofit): OsmApiService {
        return retrofit.create(OsmApiService::class.java)
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OSM