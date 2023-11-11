package com.example.bervageorder.data.network

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @WriteTimeOut
    fun provideWriteTimeOut(): Long = 60L

    @Provides
    fun provideReadTimeOut(): Long = 60L

    @Provides
    fun provideConnectTimeOut(): Long = 60L

    // 1. OkhttpBuilder 생성
    @Provides
    fun provideOkhttpClient(
        @WriteTimeOut writeTimeOut: Long,
        @ReadTimeOut readTimeOut: Long,
        @ConnectTimeOut connectTimeOut: Long,
        @OkHttpLoggingInterceptor okhttpLogger: Interceptor,
        @UserAgentInterceptor userAgentInterceptor: Interceptor,
        @UserTokenInterceptor tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            // 1. write, read, connect Timeout 설정
            .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
            // 2. HttpLoggerInterCeptor추가
            .addInterceptor(okhttpLogger)
            .addInterceptor(userAgentInterceptor)
            .addInterceptor(tokenInterceptor)
        return okHttpClient.build()
    }

    // 2. OkHttpLoggerInterceptor 생성
    @Provides
    @OkHttpLoggingInterceptor
    fun provideOkHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    // 3. UserAgentInterceptor 생성
    @Provides
    fun provideUserAgentInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                // TODO UserAgent 테스트 해볼 것
                // UserAgent : <Product> / <Product-version> <comment>
                .header("User-Agent", "BeverageOrder")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
    }

    // 4. converter생성
    @Provides
    fun provideMoshiConverter(): Moshi {
        return Moshi.Builder()
//            .add()  // polymorphsim 추가
            .build()
    }

    // 5. callAdapter생성


    
    // 6. 서버 환경 설정
    @Provides
    fun provideServerList(
        testValue: String
    ): ServerList {
        return ServerList(testValue)
    }

    // 7. Retrofit생성
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        serverList: ServerList
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .addCallAdapterFactory()
            .baseUrl(serverList.getBaseUrl())
            .build()
    }
}