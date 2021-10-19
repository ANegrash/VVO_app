package com.me.nav.vvo.model

class FlightModel {
    private var status_en: String? = null
    private var terminal: String? = null
    private var bort_number: String? = null
    private var stay_number: String? = null
    private var date_and_time_plan: String? = null
    private var date_and_time_calc: String? = null
    private var date_and_time_fact: String? = null
    private var delay_time: String? = null
    private var src_port_cod: String? = null
    private var dest_port_cod: String? = null
    private var etd: String? = null
    private var eta: String? = null
    private var period_start: String? = null
    private var period_end: String? = null
    private var days: String? = null
    private var checkin_desk: String? = null
    private var checkin_begin: String? = null
    private var checkin_end: String? = null
    private var resources: String? = null
    private var reys_num: String? = null
    private var reys_Comb: String? = null
    private var aircompany_Comb: String? = null
    private var aircompany: String? = null
    private var aircompany_ext: String? = null
    private var status: String? = null
    private var litera: String? = null
    private var typevs_code: String? = null
    private var type: String? = null
    private var view: String? = null
    private var delay_code: String? = null
    private var src_city: String? = null
    private var dest_city: String? = null
    private var src_port: String? = null
    private var dest_port: String? = null
    private var path: String? = null

    fun FlightModel(
        status_en: String?,
        terminal: String?,
        bort_number: String?,
        stay_number: String?,
        date_and_time_plan: String?,
        date_and_time_calc: String?,
        date_and_time_fact: String?,
        delay_time: String?,
        src_port_cod: String?,
        dest_port_cod: String?,
        etd: String?,
        eta: String?,
        period_start: String?,
        period_end: String?,
        days: String?,
        checkin_desk: String?,
        checkin_begin: String?,
        checkin_end: String?,
        resources: String?,
        reys_num: String?,
        reys_Comb: String?,
        aircompany_Comb: String?,
        aircompany: String?,
        aircompany_ext: String?,
        status: String?,
        litera: String?,
        typevs_code: String?,
        type: String?,
        view: String?,
        delay_code: String?,
        src_city: String?,
        dest_city: String?,
        src_port: String?,
        dest_port: String?,
        path: String?
    ) {
        this.status_en = status_en
        this.terminal = terminal
        this.bort_number = bort_number
        this.stay_number = stay_number
        this.date_and_time_plan = date_and_time_plan
        this.date_and_time_calc = date_and_time_calc
        this.date_and_time_fact = date_and_time_fact
        this.delay_time = delay_time
        this.src_port_cod = src_port_cod
        this.dest_port_cod = dest_port_cod
        this.etd = etd
        this.eta = eta
        this.period_start = period_start
        this.period_end = period_end
        this.days = days
        this.checkin_desk = checkin_desk
        this.checkin_begin = checkin_begin
        this.checkin_end = checkin_end
        this.resources = resources
        this.reys_num = reys_num
        this.reys_Comb = reys_Comb
        this.aircompany_Comb = aircompany_Comb
        this.aircompany = aircompany
        this.aircompany_ext = aircompany_ext
        this.status = status
        this.litera = litera
        this.typevs_code = typevs_code
        this.type = type
        this.view = view
        this.delay_code = delay_code
        this.src_city = src_city
        this.dest_city = dest_city
        this.src_port = src_port
        this.dest_port = dest_port
        this.path = path
    }

    fun getStatus(): String? {
        return status
    }

}