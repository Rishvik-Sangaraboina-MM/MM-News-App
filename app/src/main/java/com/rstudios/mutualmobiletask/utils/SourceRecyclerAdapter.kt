package com.rstudios.mutualmobiletask.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rstudios.mutualmobiletask.R
import com.rstudios.mutualmobiletask.model.SourceX

class SourceRecyclerAdapter : RecyclerView.Adapter<SourceRecyclerAdapter.SourceHolder>() {
  val list = arrayListOf<SourceX>()
  lateinit var context: Context

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): SourceHolder {
    context = parent.context
    return SourceHolder(LayoutInflater.from(context).inflate(R.layout.layout_source, parent, false))
  }

  override fun onBindViewHolder(
    holder: SourceHolder,
    position: Int
  ) {
    val sourceX = list[position]
    holder.name.text = sourceX.name
    holder.desc.text = sourceX.description
    holder.url.text = sourceX.url
    holder.view.setOnClickListener {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse(sourceX.url)
      val chooser = Intent.createChooser(intent, "Open with")
      context.startActivity(chooser)
    }
  }

  override fun getItemCount(): Int {
    return list.size
  }

  class SourceHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.source_name)
    val desc: TextView = view.findViewById(R.id.source_desc)
    val url: TextView = view.findViewById(R.id.source_url)
  }
}