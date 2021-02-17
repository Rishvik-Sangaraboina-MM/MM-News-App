package com.rstudios.mutualmobiletask.ui

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.rstudios.mutualmobiletask.api.Status
import com.rstudios.mutualmobiletask.databinding.ActivitySearchResultsBinding
import com.rstudios.mutualmobiletask.repository.SearchRepository
import com.rstudios.mutualmobiletask.util.Constants
import com.rstudios.mutualmobiletask.util.NewsRecyclerAdapter
import com.rstudios.mutualmobiletask.util.SearchViewModelProvider
import com.rstudios.mutualmobiletask.viewmodel.SearchViewModel

class SearchResultsActivity : AppCompatActivity() {
    lateinit var viewModel: SearchViewModel
    private val binding : ActivitySearchResultsBinding by lazy { ActivitySearchResultsBinding.inflate(layoutInflater) }
    private val newsRecyclerAdapter : NewsRecyclerAdapter by lazy { NewsRecyclerAdapter() }
    companion object{
        var cacheQuery : Intent ?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchViewModelProvider = SearchViewModelProvider(application,SearchRepository())
        viewModel = ViewModelProvider(this,searchViewModelProvider).get(SearchViewModel::class.java)
        binding.lifecycleOwner=this
        setContentView(binding.root)
        binding.searchxRecyclerview.adapter=newsRecyclerAdapter
        setSupportActionBar(binding.searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        handleIntent(intent,false)
        viewModel.search.observe(this){
            when(it.status){
                Status.LOADING ->{
                    binding.progressBar4.visibility= View.VISIBLE
                    newsRecyclerAdapter.list.clear()
                }
                Status.SUCCESS -> {
                    binding.progressBar4.visibility= View.GONE
                    it.data?.let {
                        newsRecyclerAdapter.list.addAll(it.articles)
                        Log.i("Search","Success ${it.toString()}")
                    }
                }
                Status.ERROR -> {
                    binding.progressBar4.visibility= View.GONE
                    Log.i("Search","Error"+it.message)
                    Snackbar.make(findViewById(android.R.id.content),
                        it.message+"",
                        Snackbar.LENGTH_INDEFINITE).setAction("Retry"){
                        handleIntent(intent,true)
                    }.show()
                }
            }
            Log.i("Search","observer")
            newsRecyclerAdapter.notifyDataSetChanged()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent,false)
    }

    private fun handleIntent(intent: Intent?,flag : Boolean) {
        intent?.let {
            if (Intent.ACTION_SEARCH == it.action&&(cacheQuery!=intent||flag)) {
                cacheQuery=intent
                val query = it.getStringExtra(SearchManager.QUERY)
                Log.i("Query",query)
                if(query.isNotEmpty())
                    viewModel.getEverything(query.trim())
                else{
                    newsRecyclerAdapter.list.clear()
                    newsRecyclerAdapter.notifyDataSetChanged()
                }
            }
        }
    }


}