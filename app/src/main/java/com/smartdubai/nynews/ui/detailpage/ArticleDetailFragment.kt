package com.smartdubai.nynews.ui.detailpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.smartdubai.nynews.R
import com.smartdubai.nynews.databinding.FragmentArticleDetailsBinding
import com.smartdubai.nynews.util.loadImageUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private lateinit var detailsViewModel: ArticleDetailsViewModel
    private lateinit var binding: FragmentArticleDetailsBinding

    private var articleId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsViewModel = ViewModelProvider(this).get(ArticleDetailsViewModel ::class.java)

        arguments?.let {
            if (it.containsKey(ARG_ARTICLE_ID)) {
                articleId = it.getLong(ARG_ARTICLE_ID)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentArticleDetailsBinding.inflate(layoutInflater)

        detailsViewModel.getArticle().observe(viewLifecycleOwner,  {
            activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = it.title
            binding.txtTitle.text = it.title
            binding.txtPublishedDate.text = binding.txtPublishedDate.text.toString().plus(" ").plus(it.published_date)
            binding.txtByline.text = it.byline
            binding.txtSection.text = it.section
            binding.txtDescription.text = it.description

            if(it.subsection.isNotEmpty()){
                binding.txtSubsection.text = "/".plus(it.subsection)
            }

            it.media.forEach { media ->
                media.metadata.forEach { mediaMetaData ->
                        binding.imgPoster.loadImageUrl(mediaMetaData.url)
                        binding.txtCaption.text= media.caption
                }
            }
        })
        return binding.root
    }

    companion object {
        const val ARG_ARTICLE_ID = "article_id"
    }
}