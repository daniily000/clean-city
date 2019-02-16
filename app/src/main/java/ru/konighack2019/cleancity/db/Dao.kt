package ru.konighack2019.cleancity.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.konighack2019.cleancity.model.Point
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

    /* Point */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoint(pointDetails: Point)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoints(points: List<Point>)

    @Query("select * from points")
    fun getAllPoints(): LiveData<List<Point>>

    @Query("select * from points where id = :id")
    fun getPointById(id: String): LiveData<Point>

    @Query("select * from points where id = :id")
    fun getPointDetailsBlocking(id: String): Point

}