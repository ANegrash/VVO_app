package com.me.nav.vvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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

    var datesArray = arrayOf("Вчера", "Сегодня", "Завтра")
    var currentDateVar = 0

    var typesArray = arrayOf("Вылет", "Прилёт")
    var currentTypeVar = 0

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerDates = findViewById<Spinner>(R.id.spinner_dates)
        val spinnerTypes = findViewById<Spinner>(R.id.spinner_flightTypes)

        val adapterDates = ArrayAdapter(this, android.R.layout.simple_spinner_item, datesArray)
        adapterDates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDates.adapter = adapterDates
        spinnerDates.setSelection(currentDateVar)

        val adapterTypes = ArrayAdapter(this, android.R.layout.simple_spinner_item, typesArray)
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTypes.adapter = adapterTypes
        spinnerTypes.setSelection(currentTypeVar)

        val itemSelectedListenerDates: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    currentDateVar = position
                    setListViewContent(currentTypeVar, currentDateVar)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        spinnerDates.onItemSelectedListener = itemSelectedListenerDates

        val itemSelectedListenerTypes: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    currentTypeVar = position
                    setListViewContent(currentTypeVar, currentDateVar)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        spinnerTypes.onItemSelectedListener = itemSelectedListenerTypes

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