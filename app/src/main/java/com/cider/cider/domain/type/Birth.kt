package com.cider.cider.domain.type


import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class Birth(
    val year: Int,
    val month: Int,
    val day: Int
) {
    companion object {
        fun fromDate(date: Date): Birth {
            val format = SimpleDateFormat("yyyy", Locale.getDefault())
            val year = format.format(date).toInt()
            format.applyPattern("MM")
            val month = format.format(date).toInt()
            format.applyPattern("dd")
            val day = format.format(date).toInt()
            return Birth(year, month, day)
        }
    }
    fun toDate(): Date {
        val dateString = "$year-${String.format("%02d", month)}-${String.format("%02d", day)}"
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.parse(dateString)!!
    }

    fun hasPassed14Years(): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = Calendar.getInstance().apply { time = Date() }
        val birthDate = format.parse("$year-${String.format("%02d", month)}-${String.format("%02d", day)}")
        val birthCalendar = Calendar.getInstance().apply { time = birthDate }
        birthCalendar.add(Calendar.YEAR, 14)
        return birthCalendar.before(today)
    }
}