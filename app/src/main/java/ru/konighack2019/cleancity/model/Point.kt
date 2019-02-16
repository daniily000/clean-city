package ru.konighack2019.cleancity.model

class Point (
    val id: String,
    val subject: String,
    val description: String,
    val address: String,
    val name: String,
    val email: String,
    val phone: String,
    val regionName: String,
    val agree: String,
    val participate: String,
    val photo: List<String>,
    val mapPoint: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val comment: String,
    val regionId: Int,
    val photoAdmin: List<String>
)