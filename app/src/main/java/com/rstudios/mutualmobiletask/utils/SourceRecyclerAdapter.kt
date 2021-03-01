package com.rstudios.mutualmobiletask.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.local.entity.SourceXEntity
import com.rstudios.mutualmobiletask.R
import com.rstudios.mutualmobiletask.databinding.LayoutSourceBinding

class SourceRecyclerAdapter(val context: Context,val list : ArrayList<SourceXEntity>) : RecyclerView.Adapter<SourceRecyclerAdapter.SourceHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): SourceHolder {
    return SourceHolder(LayoutInflater.from(context).inflate(R.layout.layout_source, parent, false))
  }

  override fun onBindViewHolder(
    holder: SourceHolder,
    position: Int
  ) {
    holder.bind(list[position],context)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  class SourceHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = LayoutSourceBinding.bind(view)
    val name: TextView = binding.sourceName
    val desc: TextView = binding.sourceDesc
    val url: TextView = binding.sourceUrl

    fun bind(sourceXEntity: SourceXEntity,context: Context){
      name.text = sourceXEntity.name
      desc.text = sourceXEntity.description
      url.text = sourceXEntity.url
      binding.root.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(sourceXEntity.url)
        val chooser = Intent.createChooser(intent, "Open with")
        context.startActivity(chooser)
      }
    }
  }
}