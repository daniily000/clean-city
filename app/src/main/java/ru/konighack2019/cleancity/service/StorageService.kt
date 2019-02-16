package ru.konighack2019.cleancity.service

import android.arch.lifecycle.LiveData
import ru.konighack2019.cleancity.model.Point
import ru.konighack2019.cleancity.model.UserInfo

/**
 * Basic Service for database interaction </code>
 */
interface StorageService {
    /**
     * Fetches [Point] info from DB
     * @param id String with id of requested point
     * @return [Point] from DB
     */
    fun getPoint(id: String): LiveData<Point>
    /**
     * Fetches all points from local db.
     * @return List<[Point]> containing all reports
     */
    fun getAllPoints(): LiveData<List<Point>>
    /**
     * Saves [Point] in DB.
     * @param point [Point] to be saved
     * @return String with Id of saved point
     */
    fun savePoint(point: Point): String
    /**
     * Fetches User information stored in a local db
     * @return [UserInfo] from local DB
     */
    fun getUserInfo(): LiveData<UserInfo>
    /**
     * Stores [UserInfo] in local db
     * @param userInfo [UserInfo] to store
     */
    fun saveUserInfo(userInfo: UserInfo)
}