package com.josus.mynewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josus.mynewsapp.ui.data.NewsResponse
import com.josus.mynewsapp.ui.repository.NewsRepository
import com.josus.mynewsapp.ui.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
   private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage=1
    val searchedNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchPage=1

    init {
        getBreakingNews("in")

    }


    fun getBreakingNews(countryCode:String)=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response=newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))

    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }

    fun getSeacrhNews(query:String)=viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        val response=newsRepository.searchNews(query,searchPage)
        searchedNews.postValue(handleSearchNewsResponse(response))
    }


    private fun handleSearchNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }



}