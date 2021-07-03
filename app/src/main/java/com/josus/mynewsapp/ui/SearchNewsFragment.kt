package com.josus.mynewsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.josus.mynewsapp.R
import com.josus.mynewsapp.ui.adapters.NewsAdapter
import com.josus.mynewsapp.ui.util.ConnectionManager
import com.josus.mynewsapp.ui.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.josus.mynewsapp.ui.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment() {

    var viewModel: NewsViewModel? = null
    val TAG = "SearchNewsFragment"
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_news, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        if (ConnectionManager().checkConnectivity(activity as MainActivity)) {
            setupRecyclerView()

            newsAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("article", it)
                }
                findNavController().navigate(
                    R.id.action_searchNewsFragment_to_articleFragment,
                    bundle
                )
            }
        } else {
            Toast.makeText(context, R.string.offline_message, Toast.LENGTH_LONG).show()
            // Snackbar.make(view, R.string.offline_message, Snackbar.LENGTH_LONG).show()
        }


        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString()
                            .isNotEmpty() && ConnectionManager().checkConnectivity(activity as MainActivity)
                    ) {
                        viewModel?.getSeacrhNews(editable.toString())
                    } else {
                        Toast.makeText(context, "You are Offline", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        if (ConnectionManager().checkConnectivity(activity as MainActivity)) {
            viewModel?.searchedNews?.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)

                            Log.d("Fragment", response.data.totalResults.toString())

                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Log.d(TAG, "An error occurred:$message")
                        }
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            })
        } else {
            Toast.makeText(context, R.string.offline_message, Toast.LENGTH_LONG).show()
        }

    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}