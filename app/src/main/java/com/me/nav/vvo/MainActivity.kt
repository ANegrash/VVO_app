package com.me.nav.vvo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.me.nav.vvo.model.FlightModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private val YESTERDAY : Int = 0
    private val TODAY : Int = 1
    private val TOMORROW : Int = 2

    private val today = LocalDate.now()
    private val tomorrow = today.plusDays(1)
    private val yesterday = today.minusDays(1)

    private var datesArray = arrayOf("Yesterday", "Today", "Tomorrow")
    var currentDateVar = 0

    private val DEPARTURE : Int = 0
    private val ARRIVAL : Int = 1

    private var typesArray = arrayOf("Отправление", "Прибытие")
    var currentTypeVar = 0
    var phpSessionId: String = ""

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerDates = findViewById<Spinner>(R.id.spinner_dates)
        val spinnerTypes = findViewById<Spinner>(R.id.spinner_flightTypes)
        val reloadButton = findViewById<ImageButton>(R.id.reloadButton)
        val aboutButton = findViewById<ImageButton>(R.id.btn_faq)

        setFrameLayoutContent(0, 1, 0)
        getCookie()
        while (phpSessionId == "") {
            Thread.sleep(250)
        }

        datesArray[0] = "Вчера (" + yesterday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")"
        datesArray[1] = "Сегодня (" + today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")"
        datesArray[2] = "Завтра (" + tomorrow.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")"

        setListViewContent(currentTypeVar, currentDateVar)

        val adapterDates = ArrayAdapter(this, R.layout.spinner_item, datesArray)
        adapterDates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDates.adapter = adapterDates
        spinnerDates.setSelection(currentDateVar)

        val adapterTypes = ArrayAdapter(this, R.layout.spinner_item, typesArray)
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTypes.adapter = adapterTypes
        spinnerTypes.setSelection(currentTypeVar)

        val itemSelectedListenerDates: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected (
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                setFrameLayoutContent(0, 1, 0)
                currentDateVar = position
                setListViewContent(currentTypeVar, currentDateVar)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinnerDates.onItemSelectedListener = itemSelectedListenerDates

        val itemSelectedListenerTypes: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected (
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                setFrameLayoutContent(0, 1, 0)
                currentTypeVar = position
                setListViewContent(currentTypeVar, currentDateVar)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinnerTypes.onItemSelectedListener = itemSelectedListenerTypes

        reloadButton.setOnClickListener {
            setFrameLayoutContent(0, 1, 0)
            setListViewContent(currentTypeVar, currentDateVar)
        }

        aboutButton.setOnClickListener {
            val intent = Intent(applicationContext, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListViewContent (
        type: Int = DEPARTURE,
        date: Int = TODAY
    ) {
        var url = "https://vvo.aero/php/ajax_xml.php?action=filter"

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
            phpSessionId,
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        setFrameLayoutContent(0, 0, 1)
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.body != null) {
                        val stringResponse = response.body!!.string()
                        val listView = findViewById<ListView>(R.id.flightList)
                        val builder = GsonBuilder()
                        val gson: Gson = builder.create()

                        val typeToken: Type = object : TypeToken<List<FlightModel?>?>() {}.type
                        val listOfFlights: List<FlightModel> = gson.fromJson(stringResponse, typeToken)

                        runOnUiThread {
                            val stateAdapter = FlightListAdapter(this@MainActivity, R.layout.list_item, listOfFlights, type)
                            listView.adapter = stateAdapter
                            listView.onItemClickListener =
                                AdapterView.OnItemClickListener { parent, view, position, id ->
                                    run {
                                        val intent = Intent(applicationContext, FlightInfo::class.java)
                                        intent.putExtra("src", listOfFlights[position].src_port)
                                        intent.putExtra("dest", listOfFlights[position].dest_port)
                                        intent.putExtra("num", listOfFlights[position].reys_num)
                                        intent.putExtra("status", listOfFlights[position].status)
                                        intent.putExtra("checkin", listOfFlights[position].checkin_desk)
                                        intent.putExtra("gate", listOfFlights[position].stay_number)
                                        intent.putExtra("date", listOfFlights[position].date_and_time_calc)
                                        intent.putExtra("airline", listOfFlights[position].aircompany)
                                        intent.putExtra("vehicle", listOfFlights[position].typevs_code)
                                        if (type == DEPARTURE) {
                                            val regTime: String = getTrueTime(listOfFlights[position].checkin_begin) + " - " + getTrueTime(listOfFlights[position].checkin_end)
                                            intent.putExtra("regTime", regTime)
                                        }
                                        startActivity(intent)
                                    }
                                }
                            setFrameLayoutContent(1, 0, 0)
                        }
                    } else {
                        runOnUiThread {
                            setFrameLayoutContent(0, 0, 1)
                        }
                    }
                }
            }
        )
    }

    private fun setFrameLayoutContent (
        list: Int = 1,
        loading: Int = 0,
        error: Int = 0
    ) {
        val listView = findViewById<ListView>(R.id.flightList)
        val loadingLayout = findViewById<ConstraintLayout>(R.id.loading_layout)
        val wifiErr = findViewById<ConstraintLayout>(R.id.wifi_err_layout)

        when (list) {
            0 -> listView.visibility = View.GONE
            1 -> listView.visibility = View.VISIBLE
        }

        when (loading) {
            0 -> loadingLayout.visibility = View.GONE
            1 -> loadingLayout.visibility = View.VISIBLE
        }

        when (error) {
            0 -> wifiErr.visibility = View.GONE
            1 -> wifiErr.visibility = View.VISIBLE
        }
    }

    private fun getTrueTime (
        time: String
    ): String {
        val trueTime = time.split(" ").toTypedArray()
        return if (trueTime.size > 1)
            trueTime[1]
        else
            ""
    }

    private fun getCookie() {
        val getResponse = Get()

        getResponse.run(
            "https://vvo.aero/passengers/services/cloakroom/",
            "",
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    phpSessionId = ""
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val header: String = response.headers.toString()
                    phpSessionId = header.split("PHPSESSID=")[1].split(";")[0]
                }
            }
        )
    }
}