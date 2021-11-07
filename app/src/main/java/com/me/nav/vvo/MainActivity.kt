package com.me.nav.vvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val YESTERDAY : Int = 0
    private val TODAY : Int = 1
    private val TOMORROW : Int = 2

    private val DEPARTURE : Int = 0
    private val ARRIVAL : Int = 1
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListViewContent(ARRIVAL, TOMORROW)
    }

    private fun setListViewContent (
        type: Int = DEPARTURE,
        date: Int = TODAY
    ) {
        var url = "https://vvo.aero/php/ajax_xml.php?action=filter"
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
        val yesterday = today.minusDays(1)

        if (type == DEPARTURE) {
            url += "&type=departure"
        } else if (type == ARRIVAL) {
            url += "&type=arrival"
        }

        when (date) {
            YESTERDAY -> {
                url += "&date=" + yesterday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            }
            TODAY -> {
                url += "&date=" + today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            }
            TOMORROW -> {
                url += "&date=" + tomorrow.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            }
        }

        val getResponse = Get()

        getResponse.run(
            url,
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        //here will be error check
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.body != null) {
                        val jsonString = response.body!!.string()
                        //here we can work with result
                        Log.e("b3", jsonString)
                    }
                }
            }
        )

    }
}