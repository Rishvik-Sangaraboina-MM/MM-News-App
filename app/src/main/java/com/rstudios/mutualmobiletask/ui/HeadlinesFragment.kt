package com.rstudios.mutualmobiletask.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rstudios.mutualmobiletask.R
import com.rstudios.mutualmobiletask.api.Status
import com.rstudios.mutualmobiletask.databinding.FragmentHeadlinesBinding
import com.rstudios.mutualmobiletask.util.NewsRecyclerAdapter
import com.rstudios.mutualmobiletask.viewmodel.MainViewModel

class HeadlinesFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    private val binding: FragmentHeadlinesBinding by lazy { FragmentHeadlinesBinding.inflate(layoutInflater) }
    private val newsRecyclerAdapter : NewsRecyclerAdapter by lazy { NewsRecyclerAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)= binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.headlinesRecyclerview.adapter=newsRecyclerAdapter
        viewModel = (activity as MainActivity).viewModel
        viewModel.headlines.observe(viewLifecycleOwner, Observer { apiResponse ->
            when(apiResponse.status){
                Status.LOADING -> {
                    binding.progressBar.visibility=View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility=View.GONE
                    apiResponse.data?.let { newsRecyclerAdapter.list.addAll(apiResponse.data.articles) }
                }
                Status.ERROR ->{
                    binding.progressBar.visibility=View.GONE
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),
                            apiResponse.message+"",
                            Snackbar.LENGTH_INDEFINITE).setAction("Retry"){
                        viewModel.getHeadlines()
                    }.show()
                }
            }
            newsRecyclerAdapter.notifyDataSetChanged()
        })
        viewModel.selected.observe(viewLifecycleOwner){
            binding.headlinesRecyclerview.layoutManager= when(it){
                0-> LinearLayoutManager(context)
                1-> GridLayoutManager(context,2)
                2-> StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                else -> LinearLayoutManager(context)
            }
        }
    }

}