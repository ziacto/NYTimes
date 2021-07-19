package com.android.nynews.ui.news

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.nynews.databinding.ActivityArticleListBinding
import com.android.nynews.network.response.Resource
import com.android.nynews.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.android.synthetic.main.article_list.view.*

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class NewsListActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsListViewModel
    private lateinit var binding: ActivityArticleListBinding
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        observeViewModel()
        initViewBinding()
    }

     private fun init() {
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
    }

     private fun initViewBinding() {
        binding = ActivityArticleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (binding.root.article_detail_container != null) {
            twoPane = true
        }
    }

     private fun observeViewModel() {
        viewModel.resultsResponse.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    showLoading()
                }
                Resource.Status.SUCCESS -> {
                    hideLoading()

                    with(binding.includeLayout.rvArticles) {
                        adapter = it.data?.results?.let { articlesList ->
                            NewsAdapter(this@NewsListActivity, articlesList, twoPane)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    showError(it.message!!)
                }
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(msg: String) {
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = msg
    }

}