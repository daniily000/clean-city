package ru.konighack2019.cleancity.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo(
    val name: String,
    val email: String,
    val phone: String
) {
    @PrimaryKey
    var id: Int = 0
}