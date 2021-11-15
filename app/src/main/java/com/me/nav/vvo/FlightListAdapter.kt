package com.me.nav.vvo

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.me.nav.vvo.model.FlightModel

class FlightListAdapter (
    context: Context?,
    resource: Int,
    jsonObjects: List<FlightModel>,
    type: Int
) : ArrayAdapter<FlightModel?>(context!!, resource, jsonObjects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val layout: Int = resource
    private val jsonObject: List<FlightModel> = jsonObjects
    private val type: Int = type
    var warningColor = "#FF0000"

    override fun getView (
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view = inflater.inflate(layout, parent, false)
        val time = view.findViewById<TextView>(R.id.itemTime)
        val direction = view.findViewById<TextView>(R.id.itemCity)
        val status = view.findViewById<TextView>(R.id.itemStatus)
        val number = view.findViewById<TextView>(R.id.itemFlightNumber)
        val obj: FlightModel = jsonObject[position]

        time.text = getTrueTime(obj.date_and_time_calc)
        number.text = obj.reys_num

        if (obj.delay_code.isNotEmpty()) {
            status.setTextColor(Color.parseColor(warningColor))
            status.text = "Delayed for " + obj.delay_time
        } else
            status.text = obj.status

        if (type == 0)
            direction.text = obj.dest_port
        else
            direction.text = obj.src_port

        return view
    }

    private fun getTrueTime (
        time: String
    ): String {
        return time.split(" ").toTypedArray()[1]
    }
}