package com.android.nynews.di


import com.android.nynews.network.NetworkHelper
import com.android.nynews.network.api.NewsListService
import com.android.nynews.repos.NewsRepository
import com.android.nynews.room.ArticlesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Module that provides an instance of the Repository
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideNewsListRepository(networkHelper: NetworkHelper,
                                      articlesListService: NewsListService,
                                      articlesDao: ArticlesDao
    ): NewsRepository {
        return NewsRepository(networkHelper, articlesListService, articlesDao)
    }
}
