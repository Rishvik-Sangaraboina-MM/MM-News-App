package com.rstudios.mutualmobiletask.ui

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rstudios.mutualmobiletask.R
import com.rstudios.mutualmobiletask.databinding.ActivityMainBinding
import com.rstudios.mutualmobiletask.repository.MainRepository
import com.rstudios.mutualmobiletask.util.MainViewModelProviderFactory
import com.rstudios.mutualmobiletask.viewmodel.MainViewModel
import kotlinx.coroutines.NonCancellable.cancel

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController : NavController by lazy { findNavController(R.id.nav_host_fragment)  }
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainRepository = MainRepository()
        val viewModelProvider=MainViewModelProviderFactory(application,mainRepository)
        viewModel=ViewModelProvider(this,viewModelProvider).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        binding.bottomNavigationView.setupWithNavController(navController)
        setSupportActionBar(binding.mainToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu!!.findItem(R.id.option_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconified=false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.option_switch_layout->{
                showSimpleDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSimpleDialog(){
        val singleItems = arrayOf("Linear Layout", "Grid Layout", "Staggered Layout")
        var selected=0
        MaterialAlertDialogBuilder(this)
                .setTitle("Change Layout")
                .setNeutralButton("CANCEL") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("OK") { dialog, which ->
                    viewModel.setManager(selected)
                }
                .setSingleChoiceItems(singleItems, viewModel.selected.value?:0) { dialog, which ->
                    selected=which
                }
                .show()
    }
}