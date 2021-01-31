package com.example.heroesapp_v1.api


import android.content.Context
import com.example.heroesapp_v1.front.utils.Constants
import com.example.heroesapp_v1.model.ApiHeroResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/** Sample URL:   http://gateway.marvel.com/v1/public/characters?offset=0&ts=1584531642223&limit=10&
 * apikey=e1b13260fe6e390810479686622d590c&hash=e818c8efce296fe0427360bf530c72cf
 */



interface MarvelApiService {

    @GET("characters")
    fun getHeroes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Deferred<ApiHeroResponse>

    @GET("characters")
    fun searchHeroes(
        @Query("offset") offset: Int,
        @Query("nameStartsWith") nameStartsWith: String
    ): Deferred<ApiHeroResponse>



    companion object {

        operator fun invoke (appContext: Context): MarvelApiService {

            val authInterceptor = AuthInterceptor()
            val connectivityInterceptor = ConnectivityInterceptor(appContext)

            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.baseApiUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelApiService::class.java)
        }

        /** operator fun invoke:
         *  Has better syntax as we can instantiate the interface like a class
         *  e.g. MarvelApiService()
         *
         *  Alternative syntax:
         *  fun create (instead of operator fun invoke)
         *  MarvelApiService.create()
         * */
    }
}
