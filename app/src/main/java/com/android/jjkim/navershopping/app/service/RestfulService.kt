package com.android.jjkim.navershopping.app.service

import android.os.Build
import android.util.Log
import com.android.jjkim.navershopping.BuildConfig
import com.android.jjkim.navershopping.common.utils.LogUtil
import okhttp3.ConnectionSpec
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

class RestfulService {
    fun onDestroy() {
        apiInterface = null
    }

    companion object {
        private const val HTTP_READ_TIMEOUT = 5000
        private const val HTTP_CONNECT_TIMEOUT = 4000
        private var apiInterface: FsRetofitInterface? = null

        @Synchronized
        fun getInstance(): FsRetofitInterface {
            return apiInterface ?: provideApiService().also {
                apiInterface = it
            }
        }

        private fun provideApiService(): FsRetofitInterface {
            return provideRetrofit(ApiConstants.BASE_URL, provideClient(true)).create(
                FsRetofitInterface::class.java
            )
        }

        private fun provideRetrofit(baseURL: String?, client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(NullOnEmptyConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()) // post 날릴려면 필요!
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun provideClient(addParameter: Boolean): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()

            interceptor.level =
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE

            val httpClientBuilder = OkHttpClient.Builder()

            httpClientBuilder.addInterceptor { chain ->
                val url: HttpUrl
                var request = chain.request()

                request = request.newBuilder()
                    .addHeader("X-Naver-Client-Id", ApiConstants.NAVER_CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", ApiConstants.NAMER_CLIENT_SECRET)
                    .build()

                url = if (addParameter) {
                    /* 공통 parameters */
                    request.url()
                        .newBuilder()
                        //                            .addQueryParameter(APIConstants.APIKEY, appData.getApiKey())
                        //                            .addQueryParameter(APIConstants.CREDENTIAL, appData.getCredential())
                        .build()
                } else {
                    request.url().newBuilder().build()
                }

                request = request.newBuilder().url(url).build()

                LogUtil.w("API", "request url : " + request.url())

                chain.proceed(request)
            }
                .connectTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(interceptor)
            return enableTlsOnPreLollipop(httpClientBuilder).build()
        }

        private fun enableTlsOnPreLollipop(builder: OkHttpClient.Builder): OkHttpClient.Builder {
            if (Build.VERSION.SDK_INT in 16..21) {
                try {
                    val sc = SSLContext.getInstance("TLSv1.2")
                    sc.init(null, null, null)
                    builder.sslSocketFactory(TLSSocketFactory(sc.socketFactory))

                    val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build()

                    val specs: MutableList<ConnectionSpec> = ArrayList()
                    specs.apply {
                        this.add(cs)
                        this.add(ConnectionSpec.COMPATIBLE_TLS)
                        this.add(ConnectionSpec.CLEARTEXT)
                    }
                    builder.connectionSpecs(specs)
                } catch (exc: Exception) {
                    Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
                }
            }
            return builder
        }
    }
}