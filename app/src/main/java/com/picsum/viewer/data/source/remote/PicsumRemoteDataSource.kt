package com.picsum.viewer.data.source.remote

import com.picsum.viewer.data.models.ImageInfo
import com.picsum.viewer.data.models.Item
import com.picsum.viewer.retrofit.ApiService
import com.picsum.viewer.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PicsumRemoteDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val retrofitClient: ApiService
) : RemoteDataSource {

    override suspend fun getImages(page: Int?): Result<List<Item>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = retrofitClient.getImages(page)
                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Result.Success(null)
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }

    override suspend fun getImageInfo(id: Int): Result<ImageInfo> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = retrofitClient.getImageInfo(id)
                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Result.Success(null)
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }

}