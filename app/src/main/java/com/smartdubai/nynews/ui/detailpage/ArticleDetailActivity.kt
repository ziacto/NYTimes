package com.smartdubai.nynews.ui.detailpage

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.smartdubai.nynews.R
import com.smartdubai.nynews.databinding.ActivityArticleDetailsBinding
import com.smartdubai.nynews.ui.detailpage.ArticleDetailFragment.Companion.ARG_ARTICLE_ID
import com.smartdubai.nynews.ui.news.NewsListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * An activity representing a single Article detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * article details are presented side-by-side with a list of articles
 * in a [ArticleListActivity].
 */
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleDetailsViewModel
    private lateinit var binding: ActivityArticleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        initViewBinding()

        if (savedInstanceState == null) {
            val fragment = ArticleDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ARTICLE_ID, intent.getLongExtra(ARG_ARTICLE_ID, 0))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.article_detail_container, fragment)
                    .commit()
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(ArticleDetailsViewModel ::class.java)
    }

    private fun initViewBinding() {
        binding = ActivityArticleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    navigateUpTo(Intent(this, NewsListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}