package com.android.nynews.ui


import com.android.MockUtil
import com.android.nynews.room.ArticlesDao
import com.android.nynews.room.LocalDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class NewsDetailsViewModelTest: LocalDatabase() {

    private lateinit var articlesDao: ArticlesDao

    @Before
    fun init() {
        articlesDao = db.articlesDao()
    }

    @Test
    fun getArticleByIdFromDB() = runBlockingTest {
        val mockDataList = MockUtil.mockResultResponse()
        articlesDao.insertArticlesMedia(mockDataList)

        val job = launch {
            val article = articlesDao.getArticleById(100000007497420)
            val mockArticle = MockUtil.mockArticle()

            MatcherAssert.assertThat(
                article.toString(),
                CoreMatchers.`is`(mockArticle.toString())
            )
        }
        job.cancel()
    }
}