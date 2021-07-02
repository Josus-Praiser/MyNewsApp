package com.josus.mynewsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.josus.mynewsapp.R
import com.josus.mynewsapp.ui.db.ArticleDatabase
import com.josus.mynewsapp.ui.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


  var viewModel: NewsViewModel? = null
    val TAG="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val newsRepository=NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory=NewsViewModelProviderFactory(newsRepository)
      //  viewModel=NewsViewModel(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
      //  viewModel=ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

            logs(viewModel.toString())
        Log.d("MainActivity",viewModel.toString())

       bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

    }
    fun logs(msg:String){
        Log.d(TAG,msg)
    }

}