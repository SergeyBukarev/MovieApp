package com.sergeybukarev.moviestestapp.gateway.mappers

import toothpick.InjectConstructor
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@InjectConstructor
class DateMapper {

    fun mapDate(input: String?): String? {
        return if (input != null && input.isNotBlank()) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(input)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            if (date != null) {
                outputFormat.format(date)
            } else {
                null
            }
        } else {
            null
        }
    }

    fun mapDuration(minutes: Int): String {
        val mills = TimeUnit.MILLISECONDS.convert(minutes.toLong(), TimeUnit.MINUTES)
        val hr = TimeUnit.MILLISECONDS.toHours(mills)
        val min = TimeUnit.MILLISECONDS.toMinutes(mills - TimeUnit.HOURS.toMillis(hr))
        return String.format("%02dh %02dm", hr, min)
    }

}
