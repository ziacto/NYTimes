package com.android.nynews.network.response

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.nynews.room.*

/**
 * Abstract class for Room Database and entities included
 */
@Database(entities = [ResultResponse::class, Article::class, Media::class, MediaMetaData::class], version = 1, exportSchema = false)
@TypeConverters(value = [TypeResponseConverter::class])
abstract class AppDatabase : RoomDatabase() {
  abstract fun articlesDao(): ArticlesDao
}

