package com.smartdubai.nynews.di


import com.smartdubai.nynews.network.NetworkHelper
import com.smartdubai.nynews.network.api.NewsListService
import com.smartdubai.nynews.repos.NewsRepository
import com.smartdubai.nynews.room.ArticlesDao
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
