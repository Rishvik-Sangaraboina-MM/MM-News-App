package com.rstudios.mutualmobiletask.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rstudios.mutualmobiletask.R
import com.rstudios.mutualmobiletask.model.Article
import javax.inject.Inject

class NewsRecyclerAdapter constructor(val context: Context,val list : ArrayList<Article>): RecyclerView.Adapter<NewsRecyclerAdapter.ArticleHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ArticleHolder {
    return ArticleHolder(
      LayoutInflater.from(context).inflate(R.layout.layout_article, parent, false)
    )
  }

  override fun onBindViewHolder(
    holder: ArticleHolder,
    position: Int
  ) {
    val article = list[position]
    holder.title.text = article.title
    holder.desc.text = article.description
    article.urlToImage?.let {
      Glide.with(context)
        .load(Uri.parse(it))
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_image_24)
        .into(holder.image)
    }
    holder.view.setOnClickListener {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse(article.url)
      val chooser = Intent.createChooser(intent, "Open with")
      context.startActivity(chooser)
    }
  }

  override fun getItemCount(): Int {
    return list.size
  }

  class ArticleHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.news_image)
    val title: TextView = view.findViewById(R.id.news_title)
    val desc: TextView = view.findViewById(R.id.news_description)
  }
}