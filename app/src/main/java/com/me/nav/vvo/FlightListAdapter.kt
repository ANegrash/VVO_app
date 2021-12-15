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

        if (obj.reys_num.toString().indexOf("[") > -1)
            number.text = obj.reys_num.toString().split(",")[0].split("[")[1]
        else
            number.text = obj.reys_num.toString()


        if (obj.delay_code.isNotEmpty()) {
            status.setTextColor(Color.parseColor(warningColor))
            val statusText = context.resources.getString(R.string.delay_msg) + " " + getDelayTime(obj.delay_time)
            status.text = statusText
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
        val trueTime = time.split(" ").toTypedArray()
        return if (trueTime.size > 1)
            trueTime[1]
        else
            ""
    }

    private fun getDelayTime (
        time: String
    ): String {
        val timeArray = time.split(":").toTypedArray()
        var result = ""
        if (timeArray[0] != "00") {
            val hours = timeArray[0].toInt()
            result += "$hours "
            result += if (hours % 100 > 20 || hours % 100 < 5) {
                when {
                    hours % 10 == 1 -> {
                        context.resources.getString(R.string.hour_1) + " "
                    }
                    hours % 10 in 2..4 -> {
                        context.resources.getString(R.string.hour_2) + " "
                    }
                    else -> {
                        context.resources.getString(R.string.hour_3) + " "
                    }
                }
            } else {
                context.resources.getString(R.string.hour_3) + " "
            }
        }

        val minutes = timeArray[1].toInt()
        result += "$minutes "
        result += if (minutes % 100 > 20 || minutes % 100 < 5) {
            when {
                minutes % 10 == 1 -> {
                    context.resources.getString(R.string.minute_1) + " "
                }
                minutes % 10 in 2..4 -> {
                    context.resources.getString(R.string.minute_2) + " "
                }
                else -> {
                    context.resources.getString(R.string.minute_3) + " "
                }
            }
        } else {
            context.resources.getString(R.string.minute_3) + " "
        }
        return result
    }
}