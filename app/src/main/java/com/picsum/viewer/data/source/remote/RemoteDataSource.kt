package com.picsum.viewer.data.source.remote

import com.picsum.viewer.data.models.ImageInfo
import com.picsum.viewer.data.models.Item
import com.picsum.viewer.util.Result

interface RemoteDataSource {
    suspend fun getImages(page: Int?): Result<List<Item>>
    suspend fun getImageInfo(id: Int): Result<ImageInfo>
}