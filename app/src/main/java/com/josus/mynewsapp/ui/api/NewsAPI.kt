package com.josus.mynewsapp.ui.api

import com.josus.mynewsapp.BuildConfig.API_KEY
import com.josus.mynewsapp.ui.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {


    //url= https://newsapi.org/v2/everything?q=tesla&from=2021-05-22&sortBy=publishedAt&apiKey=8815938576b749cb9934a5168230414d

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode:String="in",
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String= API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String=API_KEY
    ):Response<NewsResponse>

}