package com.android.nynews.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Contains all needed database queries and transactions
 */
@Dao
interface ArticlesDao {

    /** Result Transactions **/
    @Query("SELECT * FROM ResultResponse")
    fun loadAllArticlesFlow(): Flow<ResultResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResult(resultResponse: ResultResponse)


    /** Media Transactions **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedia(media: List<Media>)


    /** MediaMetaData Transactions **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMediaMetaData(mediaMetaData: List<MediaMetaData>)


    /** Articles Transactions **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(articles: List<Article>)

    @Query("SELECT * FROM Article WHERE articleId = :id LIMIT 1")
    suspend fun getArticleById(id: Long): Article?

    @Transaction
    fun insertArticlesMedia(resultResponse: ResultResponse) {
        insertResult(resultResponse)
        insertAllArticles(resultResponse.results)
        resultResponse.results.forEach { article ->
            insertMedia(article.media)
            article.media.forEach { media ->
                insertMediaMetaData(media.metadata)
            }
        }
    }

    /** Delete All **/
    @Query("DELETE FROM Article")
    fun deleteCache()

}