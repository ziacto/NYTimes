package com.android.nynews.ui.detailpage

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.android.nynews.repos.NewsRepository
import com.android.nynews.room.Article
import kotlinx.coroutines.launch

/**
 * ViewModel to get Article details from DB
 */
class ArticleDetailsViewModel @ViewModelInject constructor(
    private val articlesRepository: NewsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val articleId = savedStateHandle.get<Long>(ArticleDetailFragment.ARG_ARTICLE_ID) ?: 0

    /** Live Data to get Article Entity **/
    private val articleLiveData: MutableLiveData<Article> = MutableLiveData()
    fun getArticle(): LiveData<Article> = articleLiveData

    init {
        viewModelScope.launch {
            articlesRepository.getArticlesDao().getArticleById(articleId)?.let {
                articleLiveData.value = it
            }
        }
    }
}
