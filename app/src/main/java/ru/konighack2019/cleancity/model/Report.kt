package ru.konighack2019.cleancity.model

import android.net.Uri

data class Report (val subject: String,
                   val description: String,
                   val address: String,
                   val name: String,
                   val email: String,
                   val phone: String,
                   val regionName: String,
                   val agree: String,
                   val participate: String,
                   val mapPoint: String,
                   val photo: List<Uri>)