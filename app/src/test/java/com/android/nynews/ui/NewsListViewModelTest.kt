package com.android.nynews.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.android.MockUtil
import com.android.nynews.BaseCoroutinesRule
import com.android.nynews.network.NetworkHelper
import com.android.nynews.network.api.NewsListService
import com.android.nynews.network.response.Resource
import com.android.nynews.repos.NewsRepository
import com.android.nynews.room.ArticlesDao
import com.android.nynews.room.ResultResponse
import com.android.nynews.ui.news.NewsListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsListViewModelTest{
    private lateinit var articleListViewModel: NewsListViewModel
    private lateinit var articlesRepository: NewsRepository
    private val articlesListService : NewsListService = mock()
    private val articleDao: ArticlesDao = mock()
    private val networkHelper : NetworkHelper = mock()

    @get:Rule
    var coroutinesRule = BaseCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        articlesRepository = NewsRepository(networkHelper, articlesListService, articleDao)
        articleListViewModel = NewsListViewModel(articlesRepository)
    }

    @Test
    fun fetchArticleListTest() = runBlockingTest {
        val mockData = MockUtil.mockResultResponse()
        whenever(articleDao.loadAllArticlesFlow().asLiveData().value).thenReturn(mockData)

        val observer: Observer<Resource<ResultResponse>> = mock()
        val observerList: Observer<ResultResponse> = mock()

        val fetchedData: LiveData<Resource<ResultResponse>> =
            articlesRepository.getResults().asLiveData()
        fetchedData.observeForever(observer)

        articleListViewModel.resultsResponse
        delay(500L)

        verify(articleDao, atLeastOnce()).loadAllArticlesFlow()
        verify(observerList).onChanged(mockData)
        fetchedData.removeObserver(observer)
    }
}