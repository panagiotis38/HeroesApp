package com.example.heroesapp_v1.api

import android.util.Log
import com.example.heroesapp_v1.exceptions.RetrofitUnauthorisedException
import com.example.heroesapp_v1.front.utils.Constants
import com.example.heroesapp_v1.front.utils.CustomEvent
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import java.math.BigInteger
import java.security.MessageDigest



class AuthInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response? {

        val initialRequest = chain.request()
        val initialUrl = initialRequest.url()
        val ts = System.currentTimeMillis()

        val url = initialUrl.newBuilder()
            .addQueryParameter("apikey", Constants.public_key)
            .addQueryParameter("hash",getHash(ts))
            .addQueryParameter("ts",ts.toString())
            .build()

        val request = initialRequest.newBuilder()
            .addHeader("apikey", Constants.public_key)
            .addHeader("hash", getHash(ts))
            .url(url)
            .build()

        var response = chain.proceed(request)

        // Handle 401 error
        var retriesCounter = 0
        while (response.code() == 401) {
            Log.e("Error 401","Retrying... Number of retry:" + (retriesCounter + 1).toString())
            retriesCounter++
            response = chain.proceed(request)
            if (retriesCounter == 5) {
                throw RetrofitUnauthorisedException()
            }
        }
        return response
    }




    private fun getHash(ts:Long):String {

        val hashStr="$ts${Constants.private_key}${Constants.public_key}"
        val messageDigest = MessageDigest.getInstance("MD5")
        return BigInteger(1, messageDigest.digest(hashStr.toByteArray())).toString(16)
    }
}