package id.sansets.infood.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import id.sansets.infood.core.BuildConfig
import id.sansets.infood.core.data.source.remote.network.CoreApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class CoreNetworkModule {

    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val chainInterceptor = { chain: Interceptor.Chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build()
            )
        }

        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(chainInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(ChuckerInterceptor(context))
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(chainInterceptor)
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()
        }
    }

    @Provides
    fun provideCoreApiService(client: OkHttpClient): CoreApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(CoreApiService::class.java)
    }
}