package com.picsum.viewer.retrofit

import com.picsum.viewer.data.models.ImageInfo
import com.picsum.viewer.data.models.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_API_URL = "https://picsum.photos/"
        const val DEFAULT_LIMIT = 20
    }

    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int?,
        @Query("limit") size: Int? = DEFAULT_LIMIT
    ): Response<List<Item>>

    @GET("id/{id}/info")
    suspend fun getImageInfo(@Path("id") id: Int): Response<ImageInfo>
}