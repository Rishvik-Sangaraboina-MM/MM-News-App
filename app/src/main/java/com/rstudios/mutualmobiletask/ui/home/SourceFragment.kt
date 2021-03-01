package com.rstudios.mutualmobiletask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rstudios.mutualmobiletask.R
import com.example.data.remote.ApiResponse.Error
import com.example.data.remote.ApiResponse.Loading
import com.example.data.remote.ApiResponse.Success
import com.rstudios.mutualmobiletask.databinding.FragmentSourceBinding
import com.rstudios.mutualmobiletask.utils.SourceRecyclerAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SourceFragment : DaggerFragment(R.layout.fragment_source) {
  lateinit var viewModel: HomeVM
  private val binding: FragmentSourceBinding by lazy {
    FragmentSourceBinding.inflate(
      layoutInflater
    )
  }

  @Inject
  lateinit var sourceRecyclerAdapter: SourceRecyclerAdapter

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
    binding.sourceRecyclerview.adapter = sourceRecyclerAdapter
    viewModel = (activity as HomeActivity).viewModel
    viewModel.sources.observe(viewLifecycleOwner, Observer { apiResponse ->
      when (apiResponse) {
        is Error -> {
          binding.progressBar2.visibility = View.GONE
          Snackbar.make(
            (activity as HomeActivity).findViewById(android.R.id.content),
            apiResponse.message + "",
            Snackbar.LENGTH_INDEFINITE
          ).setAction("Retry") {
            viewModel.getSources()
          }.show()
          apiResponse.data?.let { sourceRecyclerAdapter.list.addAll(it.sourceEntities) }
        }
        Loading -> binding.progressBar2.visibility = View.VISIBLE
        is Success -> {
          binding.progressBar2.visibility = View.GONE
          apiResponse.data?.let {
            sourceRecyclerAdapter.list.addAll(it.sourceEntities)
            viewModel.insertSources(it.sourceEntities)
          }
        }
      }
      sourceRecyclerAdapter.notifyDataSetChanged()
    })
    viewModel.selected.observe(viewLifecycleOwner) {
      binding.sourceRecyclerview.layoutManager = when (it) {
        0 -> LinearLayoutManager(context)
        1 -> GridLayoutManager(context, 2)
        2 -> StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        else -> LinearLayoutManager(context)
      }
    }
  }
}