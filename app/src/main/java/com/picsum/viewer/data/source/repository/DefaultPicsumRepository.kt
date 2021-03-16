package com.picsum.viewer.data.source.repository

import com.picsum.viewer.data.models.ImageInfo
import com.picsum.viewer.data.models.Item
import com.picsum.viewer.data.source.local.LocalDataSource
import com.picsum.viewer.data.source.remote.RemoteDataSource
import com.picsum.viewer.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultPicsumRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PicsumRepository {

    override suspend fun getItems(): Result<List<Item>> =
        withContext(ioDispatcher) {
            localDataSource.getItems().let {
                if (it.isEmpty()) {
                    getItems(1)
                } else {
                    Result.Success(it)
                }
            }
        }

    override suspend fun getItems(page: Int?): Result<List<Item>> =
        withContext(ioDispatcher) {
            when (val response = remoteDataSource.getImages(page)) {
                is Result.Success -> {
                    if (response.data == null) {
                        Result.Error(NullPointerException("Page $page has no data"))
                        remoteDataSource.getImages(1)
                    } else {
                        localDataSource.saveItems(response.data)
                        Result.Success(response.data)
                    }
                }

                is Result.Error -> {
                    Result.Error(response.exception)
                }

                else -> Result.Loading
            }
        }

    override suspend fun saveItems(items: List<Item>) =
        withContext(ioDispatcher) {
            localDataSource.saveItems(items)
        }

    override suspend fun deleteItems() =
        withContext(ioDispatcher) {
            localDataSource.deleteItems()
        }

    override suspend fun getImageInfo(id: Int): Result<ImageInfo> =
        withContext(ioDispatcher) {
            remoteDataSource.getImageInfo(id)
        }

}