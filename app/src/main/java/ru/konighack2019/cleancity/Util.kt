package ru.konighack2019.cleancity

import java.text.SimpleDateFormat
import java.util.*

fun dateToReadable(epoch: Long) : String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", AppDelegate.applicationContext().resources.configuration.locale)
    val calendar = GregorianCalendar(Calendar.getInstance().timeZone)
    calendar.timeInMillis = epoch
    return sdf.format(calendar.time)
}