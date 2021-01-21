package id.dimasferians.moviecatalogue.core.di

import dagger.Module
import dagger.Provides
import id.dimasferians.moviecatalogue.core.BuildConfig
import id.dimasferians.moviecatalogue.core.data.source.remote.network.ApiService
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class CoreNetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val chainInterceptor = { chain: Interceptor.Chain ->
            // Injecting Api Key
            val newRequestUrl =
                chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()

            chain.proceed(
                chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .url(newRequestUrl)
                    .build()
            )
        }

        val hostName = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostName, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0")
            .add(hostName, "sha256/+sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA")
            .add(hostName, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI")
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(chainInterceptor)
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

}