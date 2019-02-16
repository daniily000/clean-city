package ru.konighack2019.cleancity.service

import android.arch.lifecycle.LiveData
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.db.Dao
import ru.konighack2019.cleancity.model.Point
import ru.konighack2019.cleancity.model.UserInfo

class StorageServiceImpl: StorageService {

    private val dao: Dao = AppDelegate.instance.dao

    override fun getPoint(id: String) = dao.getPointById(id)
    override fun getAllPoints() = dao.getAllPoints()
    override fun savePoint(point: Point) = dao.insertPoint(point)
    override fun getUserInfo() = dao.getUserInfo()
    override fun saveUserInfo(userInfo: UserInfo) = dao.insertUserInfo(userInfo)

}