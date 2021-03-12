package com.smartdubai.nynews.ui.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smartdubai.nynews.R
import com.smartdubai.nynews.room.Article
import com.smartdubai.nynews.ui.detailpage.ArticleDetailActivity
import com.smartdubai.nynews.ui.detailpage.ArticleDetailFragment
import com.smartdubai.nynews.ui.detailpage.ArticleDetailFragment.Companion.ARG_ARTICLE_ID
import com.smartdubai.nynews.ui.news.NewsListActivity
import com.smartdubai.nynews.util.listen
import com.smartdubai.nynews.util.loadImageUrl
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Adapter that handles list of Articles
 */
@ExperimentalCoroutinesApi
class NewsAdapter(
    private val parentActivity: NewsListActivity,
    private val articlesList: List<Article>,
    private val twoPane: Boolean
) : RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtTitle: AppCompatTextView? = itemView.findViewById(R.id.txt_title)
        private val txtByline: AppCompatTextView? = itemView.findViewById(R.id.txt_byline)
        private val txtPublishedDate: AppCompatTextView? =
            itemView.findViewById(R.id.txt_published_date)
        private val imgThumb: AppCompatImageView? = itemView.findViewById(R.id.img_thumb)

        fun bind(article: Article) {
            article.let {
                txtTitle?.text = it.title
                txtByline?.text = it.byline
                txtPublishedDate?.text = it.published_date

                it.media.forEach { media ->
                    media.metadata.forEach { mediaMetaData ->
                            imgThumb?.loadImageUrl(mediaMetaData.url)
                    }
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_article, parent, false)
                return ItemViewHolder(
                    view
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent).listen { position ->

            if (twoPane) {
                val fragment = ArticleDetailFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_ARTICLE_ID, articlesList[position].articleId)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.article_detail_container, fragment)
                    .commit()
            } else {
                ContextCompat.startActivity(
                    parent.context,
                    Intent(parent.context, ArticleDetailActivity::class.java)
                        .putExtra(ARG_ARTICLE_ID, articlesList[position].articleId), null
                )
            }
        }
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(articlesList[position])
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }
}