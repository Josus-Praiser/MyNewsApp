package com.josus.mynewsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josus.mynewsapp.ui.repository.NewsRepository

class NewsViewModelProviderFactory(
   private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsRepository) as T

    }
}

    /*
     if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
           }
        throw IllegalArgumentException("Unknown ViewModel class")
     */