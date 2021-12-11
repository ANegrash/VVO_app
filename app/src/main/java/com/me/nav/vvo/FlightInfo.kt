package com.me.nav.vvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class FlightInfo : AppCompatActivity() {
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_info)

        val intent = intent
        val tvFlight = findViewById<TextView>(R.id.textview_flight)
        val tvFrom = findViewById<TextView>(R.id.textview_first_flight)
        val tvTo = findViewById<TextView>(R.id.textview_last_flight)
        val tvStatus = findViewById<TextView>(R.id.textView_flight_status)
        val tvCheckin = findViewById<TextView>(R.id.textView_reception_desks_numbers)
        val tvExits = findViewById<TextView>(R.id.textView_exit_number)
        val tvAirline = findViewById<TextView>(R.id.textView_company_name)
        val tvDate = findViewById<TextView>(R.id.textView_plan_date_current)
        val tvVehicle = findViewById<TextView>(R.id.tvVehicle)
        val tvRegTime = findViewById<TextView>(R.id.textView_reception_time_data)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        val srcPort = intent.getStringExtra("src")
        val destPort = intent.getStringExtra("dest")
        val flightNumber = intent.getStringExtra("num")
        val flightStatus = intent.getStringExtra("status")
        var checkinDesks = intent.getStringExtra("checkin")
        var regTime = intent.getStringExtra("regTime")
        val gate = intent.getStringExtra("gate")
        val date = intent.getStringExtra("date")
        val airline = intent.getStringExtra("airline")
        val vehicle = intent.getStringExtra("vehicle")

        if (regTime == null) {
            checkinDesks = "-"
            regTime = "-"
        }

        tvFlight.text = flightNumber
        tvFrom.text = srcPort
        tvTo.text = destPort
        tvStatus.text = flightStatus
        tvCheckin.text = checkinDesks
        tvRegTime.text = regTime
        tvExits.text = gate
        tvAirline.text = airline
        tvDate.text = date
        tvVehicle.text = vehicle

        backBtn.setOnClickListener {
            finish()
        }

    }
}