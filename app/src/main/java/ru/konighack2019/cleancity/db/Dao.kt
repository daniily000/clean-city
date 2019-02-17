package ru.konighack2019.cleancity.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.konighack2019.cleancity.model.EsooEntry
import ru.konighack2019.cleancity.model.KSEntry
import ru.konighack2019.cleancity.model.UserInfo

@Dao
interface Dao {

    /* UserInfo */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfo)

    @Query("select * from user_info where id = 0")
    fun getUserInfo(): LiveData<UserInfo>

    @Query("select * from user_info where id = 0")
    fun getUserInfoBlocking(): UserInfo

    /* KSEntry */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoint(KSEntryDetails: KSEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoints(KSEntries: List<KSEntry>)

    @Query("select * from ksentries")
    fun getAllKSReports(): LiveData<List<KSEntry>>

    @Query("select * from esooentries")
    fun getAllEsooReports(): LiveData<List<EsooEntry>>

    @Query("select * from ksentries where id = :id")
    fun getPointById(id: String): LiveData<KSEntry>

    @Query("select * from ksentries where id = :id")
    fun getPointDetailsBlocking(id: String): KSEntry

}