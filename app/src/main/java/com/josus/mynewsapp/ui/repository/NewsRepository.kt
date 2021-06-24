package com.josus.mynewsapp.ui.repository

import com.josus.mynewsapp.ui.api.RetrofitInstance
import com.josus.mynewsapp.ui.db.ArticleDatabase

class NewsRepository(
    val db:ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(query:String,pageNumber:Int)=
        RetrofitInstance.api.getAllNews(query,pageNumber)

}