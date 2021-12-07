package com.me.nav.vvo

import android.util.Log
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request

class Get {

    fun run(
        url: String,
        cookie: String,
        callback: Callback
    ) {
        val okHttpClient = OkHttpClient()

        if (cookie == "") {
            val request = Request.Builder()
                .url(url)
                .build()
            okHttpClient.newCall(request).enqueue(callback)
        } else {
            val request = Request.Builder()
                .url(url)
                .addHeader("Cookie", "PHPSESSID=" + cookie)
                .build()
            Log.e("TAG", request.toString())
            okHttpClient.newCall(request).enqueue(callback)
        }
    }
}