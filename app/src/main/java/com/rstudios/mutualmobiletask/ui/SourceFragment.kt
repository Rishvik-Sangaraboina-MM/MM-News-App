package com.rstudios.mutualmobiletask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rstudios.mutualmobiletask.R
import com.rstudios.mutualmobiletask.api.Status
import com.rstudios.mutualmobiletask.databinding.FragmentSourceBinding
import com.rstudios.mutualmobiletask.util.SourceRecyclerAdapter
import com.rstudios.mutualmobiletask.viewmodel.MainViewModel

class SourceFragment : Fragment(R.layout.fragment_source) {
    lateinit var viewModel: MainViewModel
    private val binding : FragmentSourceBinding by lazy { FragmentSourceBinding.inflate(layoutInflater) }
    private val sourceRecyclerAdapter : SourceRecyclerAdapter by lazy { SourceRecyclerAdapter() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)= binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sourceRecyclerview.adapter=sourceRecyclerAdapter
        viewModel = (activity as MainActivity).viewModel
        viewModel.sources.observe(viewLifecycleOwner, Observer { apiResponse ->
            when(apiResponse.status){
                Status.LOADING -> {
                    binding.progressBar2.visibility=View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar2.visibility=View.GONE
                    apiResponse.data?.let { sourceRecyclerAdapter.list.addAll(it.sources) }
                }
                Status.ERROR -> {
                    binding.progressBar2.visibility=View.GONE
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),
                            apiResponse.message + "",
                            Snackbar.LENGTH_INDEFINITE).setAction("Retry"){
                                viewModel.getSources()
                    }.show()
                }
            }
            sourceRecyclerAdapter.notifyDataSetChanged()
        })
        viewModel.selected.observe(viewLifecycleOwner){
            binding.sourceRecyclerview.layoutManager= when(it){
                0-> LinearLayoutManager(context)
                1-> GridLayoutManager(context,2)
                2-> StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                else -> LinearLayoutManager(context)
            }
        }
    }
}