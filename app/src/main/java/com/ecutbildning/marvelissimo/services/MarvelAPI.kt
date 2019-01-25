package com.ecutbildning.marvelissimo.services

import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.extensions.md5
import com.google.gson.GsonBuilder
import retrofit2.http.GET
import java.util.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelAPI {

    @GET("characters")
    fun getAllCharacters(): Observable<Response>

    @GET("characters")
    fun getAllCharactersBySearchWord(@Query("nameStartsWith") searchWord : String): Observable<Response>

    @GET("characters/{id}")
    fun getCharacterById(@Path("id") id: String) : Observable<Response>

    companion object {
        fun getService(): MarvelAPI {

            val httpClient = OkHttpClient.Builder()

            //Adding interceptor to client, appending client credentials and necessary parameters to request
            httpClient.addInterceptor{chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", "4086b80b8666989ae93621b0ef557f24")
                    .addQueryParameter("ts", ts)
                    .addQueryParameter("hash", (ts + "6c386089d3fb48baa459ab9d226b1fe765f26e60" + "4086b80b8666989ae93621b0ef557f24").md5())
                    .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com/v1/public/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

            return retrofit.create<MarvelAPI>(MarvelAPI::class.java)
        }
    }

}