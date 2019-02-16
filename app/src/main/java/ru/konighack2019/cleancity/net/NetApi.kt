package ru.konighack2019.cleancity.net

import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import ru.konighack2019.cleancity.model.Point

interface NetApi {

    /* KartaSvalok.ru */
    @GET("port_query/{id}")
    fun getPointById(@Path("id") id: String): Deferred<Point>

    @GET("points")
    fun getAllPoints(): Deferred<List<Point>>

    @POST("requests")
    fun postKSReport(@Body multipartBody: MultipartBody): Deferred<String>

    /* Kgdesoo.ru */
    @POST("requests")
    fun postEsooReport(@Body multipartBody: MultipartBody): Deferred<String>

    /* FOR TESTING PURPOSES ONLY */
    @DELETE("cleardb")
    fun deleteAllPoints(): Call<Void>
}