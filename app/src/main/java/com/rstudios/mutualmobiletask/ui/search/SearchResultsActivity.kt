package com.rstudios.mutualmobiletask.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.rstudios.mutualmobiletask.utils.MyApplication
import com.rstudios.mutualmobiletask.api.Status
import com.rstudios.mutualmobiletask.databinding.ActivitySearchResultsBinding
import com.rstudios.mutualmobiletask.utils.NewsRecyclerAdapter
import com.rstudios.mutualmobiletask.utils.SearchVMProviderFactory
import javax.inject.Inject

class SearchResultsActivity : AppCompatActivity() {
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
    (application as MyApplication).appComponent.inject(this)
    super.onCreate(savedInstanceState)
    VM = ViewModelProvider(this, searchVMProviderFactory).get(SearchVM::class.java)
    binding.lifecycleOwner = this
    setContentView(binding.root)
    binding.searchxRecyclerview.adapter = newsRecyclerAdapter
    setSupportActionBar(binding.searchToolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    handleIntent(intent, false)
    VM.search.observe(this) {
      when (it.status) {
        Status.LOADING -> {
          binding.progressBar4.visibility = View.VISIBLE
          newsRecyclerAdapter.list.clear()
        }
        Status.SUCCESS -> {
          binding.progressBar4.visibility = View.GONE
          it.data?.let {
            newsRecyclerAdapter.list.addAll(it.articles)
            Log.i("Search", "Success $it")
          }
        }
        Status.ERROR -> {
          binding.progressBar4.visibility = View.GONE
          Log.i("Search", "Error" + it.message)
          Snackbar.make(
            findViewById(android.R.id.content),
            it.message + "",
            Snackbar.LENGTH_INDEFINITE
          ).setAction("Retry") {
            handleIntent(intent, true)
          }.show()
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