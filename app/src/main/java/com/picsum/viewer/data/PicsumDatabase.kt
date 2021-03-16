package com.picsum.viewer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picsum.viewer.data.models.Item
import com.picsum.viewer.data.source.local.dao.PicsumDao

@Database(entities = [Item::class], version = 2, exportSchema = false)
abstract class PicsumDatabase : RoomDatabase() {
    abstract fun dao(): PicsumDao

    companion object {

        @Volatile
        private var instance: PicsumDatabase? = null

        fun getDatabase(context: Context): PicsumDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, PicsumDatabase::class.java, "characters")
                .fallbackToDestructiveMigration()
                .build()
    }
}