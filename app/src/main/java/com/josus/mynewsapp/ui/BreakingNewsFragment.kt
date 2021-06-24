package com.josus.mynewsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.josus.mynewsapp.R
import com.josus.mynewsapp.ui.adapters.NewsAdapter
import com.josus.mynewsapp.ui.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment() {
      var viewModel:NewsViewModel?=null
    lateinit var newsAdapter:NewsAdapter
    val TAG="BreakingNewsFragment"
 //private  val viewModel:NewsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)

      // viewModel = (activity as MainActivity).viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel = (activity as MainActivity).viewModel


        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)
        }
        try {
            viewModel?.breakingNews?.observe(viewLifecycleOwner, { response->
                when(response){
                    is Resource.Success->{
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                            Log.d("Fragment",response.data.totalResults.toString())

                        }
                    }

                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let {message->
                            Log.d(TAG,"An error occured:$message")
                        }
                    }

                    is Resource.Loading->{
                        showProgressBar()
                    }
                }
            })
        }
        catch (e:Exception){
            Log.d("Fragment",e.toString())
        }

    }

    private fun hideProgressBar() {
      paginationProgressBar.visibility=View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility=View.VISIBLE
    }

    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }


}