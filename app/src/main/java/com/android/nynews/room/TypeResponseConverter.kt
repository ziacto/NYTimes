package com.android.nynews.network.response

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.android.nynews.room.Article
import com.android.nynews.room.Media
import com.android.nynews.room.MediaMetaData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

/**
 * Contains custom objects conversions in order to save them to Room database
 */
@ProvidedTypeConverter
class TypeResponseConverter @Inject constructor(private val moshi: Moshi){

    /** Convert Article Type **/
    @TypeConverter
    fun fromStringToArticleType(value: String): List<Article>? {
        val listType = Types.newParameterizedType(List::class.java, Article::class.java)
        val adapter: JsonAdapter<List<Article>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

  @TypeConverter
  fun fromArticleType(type: List<Article>): String {
    val listType = Types.newParameterizedType(List::class.java, Article::class.java)
    val adapter: JsonAdapter<List<Article>> = moshi.adapter(listType)
    return adapter.toJson(type)
  }

    /** Convert Media Type **/
    @TypeConverter
    fun fromStringToMediaType(value: String): List<Media>? {
        val listType = Types.newParameterizedType(List::class.java, Media::class.java)
        val adapter: JsonAdapter<List<Media>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromMediaType(type: List<Media>): String {
        val listType = Types.newParameterizedType(List::class.java, Media::class.java)
        val adapter: JsonAdapter<List<Media>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    /** Convert MediaMetaData Type **/
    @TypeConverter
    fun fromStringToMediaMetaDataType(value: String): List<MediaMetaData>? {
        val listType = Types.newParameterizedType(List::class.java, MediaMetaData::class.java)
        val adapter: JsonAdapter<List<MediaMetaData>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromMediaMetaDataType(type: List<MediaMetaData>): String {
        val listType = Types.newParameterizedType(List::class.java, MediaMetaData::class.java)
        val adapter: JsonAdapter<List<MediaMetaData>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

}
