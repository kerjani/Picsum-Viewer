package com.picsum.viewer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "items")
data class Item(
    @SerializedName("author")
    val author: String?,
    @SerializedName("author_url")
    val authorUrl: String?,
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("format")
    val format: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    @PrimaryKey
    val id: Int,
    @SerializedName("post_url")
    val postUrl: String?,
    @SerializedName("width")
    val width: Int?
)