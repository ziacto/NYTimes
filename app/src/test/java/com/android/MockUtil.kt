package com.android

import com.android.nynews.room.Article
import com.android.nynews.room.Media
import com.android.nynews.room.MediaMetaData
import com.android.nynews.room.ResultResponse


object MockUtil {

    fun mockResultResponse() = ResultResponse(
        resultId = 0,
        status = "OK",
        copyright = "Copyright (c) 2021 The New York Times Company.  All Rights Reserved.",
        num_results = 20,
        results = mockArticleList())

    // Mock News
    fun mockArticle() = Article(
        articleId = 100000007641288,
        uri = "nyt://article/ac02e08f-7516-5e98-80e8-7a9893909fdf",
        url = "https://www.nytimes.com/2021/03/07/world/europe/oprah-interview-harry-meghan.html",
        source = "New York Times",
        published_date = "2021-03-07",
        section = "World",
        subsection = "Europe",
        byline = "By Mark Landler",
        title = "‘I Just Didn’t Want to Be Alive Anymore’: Meghan Says Life as Royal Made Her Suicidal",
        description = "In a bombshell interview with Oprah Winfrey, the Duchess of Sussex said she had asked officials at Buckingham Palace for medical help but was told it would damage the institution.",
        media = mockMediaList()
    )

    fun mockArticleList() = listOf(mockArticle())

    // Mock Media
    fun mockMedia() = Media(
        mediaId = 0,
        caption = "Oprah Winfrey’s highly anticipated two-hour interview with Prince Harry and his wife, Meghan, aired on CBS Sunday night.",
        metadata = mockMediaMetaDataList()
    )

    fun mockMediaList() = listOf(mockMedia())


    fun mockMediaMetaData() = MediaMetaData(
        metaDataId = 0, url = "", format = ""
    )

    fun mockMediaMetaDataList() = listOf(mockMediaMetaData())
}
