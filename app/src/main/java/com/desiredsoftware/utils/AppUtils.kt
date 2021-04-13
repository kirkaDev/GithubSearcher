package com.desiredsoftware.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

    fun getRussianDateFormat (dateForReformatting: String) : String {
        val inDateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outDateFormat: DateFormat =
            SimpleDateFormat("MM.dd.yyyy")
        val inDate : Date = inDateFormat.parse(dateForReformatting)

        return outDateFormat.format(inDate)
    }
