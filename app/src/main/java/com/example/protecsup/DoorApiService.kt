package com.example.protecsup

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DoorApiService {
    @GET("puertaapi")
    suspend fun selectDoor(): ArrayList<DoorModel>

    @GET("puertaapi/{id}")
    suspend fun selectDoor(@Path("id") id:String): Response<DoorModel>

    @Headers("Content-Type: application/json")
    @POST("puertaapi")
    suspend fun insertDoor(@Body door: DoorModel): Response<DoorModel>

    @PUT("puertaapi/{id}")
    suspend fun updateDoor(@Path("id") id:String, @Body door:DoorModel): Response<DoorModel>

    @DELETE("puertaapi/{id}")
    suspend fun  deleteDoor(@Path("id") id:String): Response<DoorModel>
}