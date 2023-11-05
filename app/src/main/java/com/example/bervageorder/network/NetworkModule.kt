package com.example.bervageorder.network

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @WriteTimeOut
    fun provideWriteTimeOut(): Long = 60L

    @Provides
    @ReadTimeOut
    fun provideReadTimeOut(): Long = 60L

    @Provides
    @ConnectTimeOut
    fun provideConnectTimeOut(): Long = 60L

    @Provides
    @LogInterceptor
    fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY) // debug fold : Body, release fold : NONE
        }
    }

    @Provides
    @UserAgentInterceptor
    fun provideUserAgentInterceptor(
        @ApplicationContext context: Context,
    ): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header(
                    "User-Agent",
                    // User-Agent: <product> / <product-version> <comment>
                    "BervageOrder/${context.appVersion} ${System.getProperty("http.agent")}",
                )
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @UserTokenInterceptor
    fun provideUserTokenInterceptor(
        userToken: Flow<String?>,
    ): Interceptor {
        return TokenInterceptor(userToken)
    }

    @Singleton
    @Provides
    fun provideMosshi(): Moshi = Moshi.Builder()
        // .add() // add adapter Polimorphism
        .build()

    @Provides
    fun provideServerList(
        testValue: String,
    ): ServerList {
        return ServerList(testValue) // debug testValue, prod "PROD"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @WriteTimeOut writeTimeOut: Long,
        @ReadTimeOut readTimeOut: Long,
        @ConnectTimeOut connectTimeOut: Long,
        @LogInterceptor logInterceptor: Interceptor,
        @UserAgentInterceptor userAgentInterceptor: Interceptor,
        @UserTokenInterceptor userTokenInterceptor: Interceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(userAgentInterceptor)
            .addInterceptor(userTokenInterceptor)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        serverList: ServerList,
        resultCallAdapter: ResultCallAdapter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(resultCallAdapter)
            .baseUrl(serverList.getBaseUrl())
            .client(okHttpClient)
            .build()
    }
}
