package com.android.nynews.room


import com.android.MockUtil.mockArticle
import com.android.MockUtil.mockArticleList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class NewsTest : LocalDatabase() {

    private lateinit var articlesDao: ArticlesDao

    @Before
    fun init() {
        articlesDao = db.articlesDao()
    }

    @Test
    fun insertAndLoadArticleListTest() = runBlockingTest {
        val mockDataList = mockArticleList()
        articlesDao.insertAllArticles(mockDataList)

        val loadFromDB = articlesDao.loadAllArticlesFlow()
        val mockData = mockArticle()

        val job = launch {
            loadFromDB.collect { value: ResultResponse ->
                MatcherAssert.assertThat(value.toString(), `is`(mockDataList.toString()))
                MatcherAssert.assertThat(value.results[0].toString(), `is`(mockData.toString()))
            }
        }
        job.cancel()
    }

}