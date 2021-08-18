package com.example.note_crud

import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    @GET("data.php")
    fun data() : Call<MainModel>

    @FormUrlEncoded
    @POST("create.php")
    fun create(@Field("note") note: String) : Call<SubmitModel>

    @FormUrlEncoded
    @PUT("update.php")
    fun update(@Field("id") id: String, @Field("note") note: String) : Call<SubmitModel>


    @DELETE("delete.php/")
    fun delete(@Body("id") id: String): Call<SubmitModel>

}