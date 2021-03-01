package com.rstudios.mutualmobiletask.ui.search

import android.R.id
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.example.data.remote.ApiResponse.Error
import com.example.data.remote.ApiResponse.Loading
import com.example.data.remote.ApiResponse.Success
import com.rstudios.mutualmobiletask.databinding.ActivitySearchResultsBinding
import com.rstudios.mutualmobiletask.utils.NewsRecyclerAdapter
import com.rstudios.mutualmobiletask.utils.SearchVMProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SearchResultsActivity : DaggerAppCompatActivity() {
  @Inject
  lateinit var searchVMProviderFactory: SearchVMProviderFactory
  lateinit var VM: SearchVM
  private val binding: ActivitySearchResultsBinding by lazy {
    ActivitySearchResultsBinding.inflate(
      layoutInflater
    )
  }
  @Inject
  lateinit var newsRecyclerAdapter: NewsRecyclerAdapter

  companion object {
    var cacheQuery: Intent? = null
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    VM = ViewModelProvider(this, searchVMProviderFactory).get(SearchVM::class.java)
    binding.lifecycleOwner = this
    setContentView(binding.root)
    // newsRecyclerAdapter = NewsRecyclerAdapter(this, arrayListOf<Article>())
    binding.searchxRecyclerview.adapter = newsRecyclerAdapter
    setSupportActionBar(binding.searchToolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    handleIntent(intent, false)
    VM.search.observe(this) {
      when (it) {
        is Error -> {
          binding.progressBar4.visibility = View.GONE
          Log.i("Search", "Error" + it.message)
          Snackbar.make(
            findViewById(id.content),
            it.message + "",
            Snackbar.LENGTH_INDEFINITE
          ).setAction("Retry") {
            handleIntent(intent, true)
          }.show()
        }
        Loading -> {
          binding.progressBar4.visibility = View.VISIBLE
          newsRecyclerAdapter.list.clear()
        }
        is Success -> {
          binding.progressBar4.visibility = View.GONE
          it.data?.let {
            newsRecyclerAdapter.list.addAll(it.articleEntities)
            Log.i("Search", "Success $it")
          }
        }
      }
      Log.i("Search", "observer")
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
    handleIntent(intent, false)
  }

  private fun handleIntent(
    intent: Intent?,
    flag: Boolean
  ) {
    intent?.let {
      if (Intent.ACTION_SEARCH == it.action && (cacheQuery != intent || flag)) {
        cacheQuery = intent
        val query = it.getStringExtra(SearchManager.QUERY)
        Log.i("Query", query)
        if (query.isNotEmpty())
          VM.getEverything(query.trim())
        else {
          newsRecyclerAdapter.list.clear()
          newsRecyclerAdapter.notifyDataSetChanged()
        }
      }
    }
  }
}