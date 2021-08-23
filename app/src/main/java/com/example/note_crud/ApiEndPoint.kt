package com.example.note_crud

import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    @GET("notes")
    fun data() : Call<MainModel>

    @FormUrlEncoded
    @POST("notes")
    fun create(@Field("note") note: String) : Call<SubmitModel>

    @FormUrlEncoded
    @PUT("notes/{id}")
    fun update(@Path("id") id: String, @Field("note") note: String) : Call<SubmitModel>

    @DELETE("notes/{id}")
    fun delete(@Path("id") id: String): Call<SubmitModel>

    @GET("notes/search/{search}")
    fun search(@Path("search") search: String): Call<MainModel>

}