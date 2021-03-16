package com.picsum.viewer.data.source.local

import com.picsum.viewer.data.models.Item
import com.picsum.viewer.data.source.local.dao.PicsumDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PicsumLocalDataSource @Inject constructor(
    private val dao: PicsumDao,
    private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {
    override suspend fun getItems(): List<Item> = withContext(ioDispatcher) {
        return@withContext dao.getItems()
    }

    override suspend fun saveItems(items: List<Item>) = withContext(ioDispatcher) {
        return@withContext dao.insertAll(items)
    }

    override suspend fun deleteItems() = withContext(ioDispatcher) {
        return@withContext dao.deleteAll()
    }
}