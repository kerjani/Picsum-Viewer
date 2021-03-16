package com.picsum.viewer.data.source.repository

import com.picsum.viewer.data.models.ImageInfo
import com.picsum.viewer.data.models.Item
import com.picsum.viewer.util.Result

interface PicsumRepository {
    suspend fun getItems(): Result<List<Item>>
    suspend fun getItems(page: Int?): Result<List<Item>>
    suspend fun saveItems(items: List<Item>)
    suspend fun deleteItems()
    suspend fun getImageInfo(id: Int): Result<ImageInfo>
}