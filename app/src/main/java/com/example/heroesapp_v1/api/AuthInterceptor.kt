package com.example.heroesapp_v1.api

import com.example.heroesapp_v1.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
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
            .addHeader("apikey", Constants.public_key)
            .url(url)
            .build()

        return chain.proceed(request)
    }


    private fun getHash(ts:Long):String {

        val hashStr="$ts${Constants.private_key}${Constants.public_key}"
        val messageDigest = MessageDigest.getInstance("MD5")
        return BigInteger(1, messageDigest.digest(hashStr.toByteArray())).toString(16)
    }
}