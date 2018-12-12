package xyz.rakalabs.englishfootball.utils

import java.text.SimpleDateFormat
import java.util.*

object UtilDate {
    fun getSheduleDate(date: String, time: String, type: Boolean): String {
        val scheduler = "$date ${time.substringBefore("+")}"
        val sdfTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdfTime.timeZone = TimeZone.getTimeZone("UTC")
        val gmtTime = sdfTime.parse(scheduler)
        return if (type) // this is date schedule
            SimpleDateFormat("E dd,MMM yyyy").format(gmtTime.time).toString()
        else // this is time scheduler
            SimpleDateFormat("HH:mm:ss").format(gmtTime.time).toString()
    }

    fun getSpecificDate(date: String, time: String, type: SpecificDate): Int {
        val scheduler = "$date ${time.substringBefore("+")}"
        val sdfTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdfTime.timeZone = TimeZone.getTimeZone("UTC")
        val gmtTime = sdfTime.parse(scheduler)
        return when (type) {
            SpecificDate.Day -> gmtTime.day
            SpecificDate.Month -> gmtTime.month
            SpecificDate.Year -> gmtTime.year
            SpecificDate.Hour -> gmtTime.hours
            SpecificDate.Minute -> gmtTime.minutes
        }
    }

    enum class SpecificDate{
        Day,
        Month,
        Year,
        Hour,
        Minute
    }
}