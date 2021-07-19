package com.android.nynews.util

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.android.nynews.R
import kotlinx.coroutines.Job

/**
 * Extension to set OnClick event on a recyclerview item
 */
fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition)
    }
    return this
}

/**
 * Extension to cancel current job if active
 */
fun Job?.cancelIfActive() {
    if (this?.isActive == true)
        cancel()
}

/**
 * Extension to render and cash imageUrl into ImageView
 */
fun ImageView.loadImageUrl(imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.placeholder)
        .into(this)
}

