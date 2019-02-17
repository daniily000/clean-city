package ru.konighack2019.cleancity.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.konighack2019.cleancity.model.KSEntry
import ru.konighack2019.cleancity.model.UserInfo

@Database(entities = [UserInfo::class, KSEntry::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase(){
    abstract fun getKSDao(): Dao
}