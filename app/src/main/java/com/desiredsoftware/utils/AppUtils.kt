package com.desiredsoftware.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

    fun getRussianDateFormat (dateForReformatting: String) : String {
        val inDateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

        val outDateFormat: DateFormat =
            SimpleDateFormat("dd.MM.yyyy")

        val inDate : Date = inDateFormat.parse(dateForReformatting)

        return outDateFormat.format(inDate)
    }
