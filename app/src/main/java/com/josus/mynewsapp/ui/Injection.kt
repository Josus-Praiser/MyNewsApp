package com.josus.mynewsapp.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.josus.mynewsapp.ui.db.ArticleDatabase
import com.josus.mynewsapp.ui.repository.NewsRepository

object Injection {
    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideRepository(context: Context): NewsRepository {
        return NewsRepository(ArticleDatabase(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): NewsViewModelProviderFactory {
        return NewsViewModelProviderFactory(provideRepository(context))
    }
}