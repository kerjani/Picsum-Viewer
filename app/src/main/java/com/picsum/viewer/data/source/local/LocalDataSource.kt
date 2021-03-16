package com.picsum.viewer.data.source.local

import com.picsum.viewer.data.models.Item

interface LocalDataSource {
    suspend fun getItems(): List<Item>
    suspend fun saveItems(items: List<Item>)
    suspend fun deleteItems()
}