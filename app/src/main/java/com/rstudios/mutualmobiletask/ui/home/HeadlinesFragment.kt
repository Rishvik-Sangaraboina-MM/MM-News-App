package com.rstudios.mutualmobiletask.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rstudios.mutualmobiletask.api.Status
import com.rstudios.mutualmobiletask.databinding.FragmentHeadlinesBinding
import com.rstudios.mutualmobiletask.model.Article
import com.rstudios.mutualmobiletask.utils.NewsRecyclerAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HeadlinesFragment : DaggerFragment() {
  lateinit var viewModel: HomeVM
  private val binding: FragmentHeadlinesBinding by lazy {
    FragmentHeadlinesBinding.inflate(
      layoutInflater
    )
  }
  @Inject
  lateinit var newsRecyclerAdapter: NewsRecyclerAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ) = binding.root

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    // newsRecyclerAdapter= NewsRecyclerAdapter(requireContext(), arrayListOf<Article>())
    binding.headlinesRecyclerview.adapter = newsRecyclerAdapter
    viewModel = (activity as HomeActivity).viewModel
    viewModel.headlines.observe(viewLifecycleOwner, { apiResponse ->
      when (apiResponse.status) {
        Status.LOADING -> {
          binding.progressBar.visibility = View.VISIBLE
        }
        Status.SUCCESS -> {
          binding.progressBar.visibility = View.GONE
          apiResponse.data?.let { newsRecyclerAdapter.list.addAll(apiResponse.data.articles) }
        }
        Status.ERROR -> {
          binding.progressBar.visibility = View.GONE
          Snackbar.make(
            (activity as HomeActivity).findViewById(android.R.id.content),
            apiResponse.message + "",
            Snackbar.LENGTH_INDEFINITE
          ).setAction("Retry") {
            viewModel.getHeadlines()
          }.show()
        }
      }
      newsRecyclerAdapter.notifyDataSetChanged()
    })
    viewModel.selected.observe(viewLifecycleOwner) {
      binding.headlinesRecyclerview.layoutManager = when (it) {
        0 -> LinearLayoutManager(context)
        1 -> GridLayoutManager(context, 2)
        2 -> StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        else -> LinearLayoutManager(context)
      }
    }
  }
}