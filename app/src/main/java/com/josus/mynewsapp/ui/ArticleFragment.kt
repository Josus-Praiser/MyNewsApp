package com.josus.mynewsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.josus.mynewsapp.R
import com.josus.mynewsapp.ui.util.ConnectionManager
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment() {


  var viewModel:NewsViewModel?=null
    val args:ArticleFragmentArgs by navArgs()
    val TAG="ArticleFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=(activity as MainActivity).viewModel

        val article=args.article

        if (ConnectionManager().checkConnectivity(activity as MainActivity)){
            webView.apply {
                webViewClient= WebViewClient()
                loadUrl(article.url)
            }

            try {
                fab.setOnClickListener {
                    viewModel?.saveArticle(article)
                    Snackbar.make(view,"Article Saved Successfully", Snackbar.LENGTH_SHORT).show()
                }
            }
            catch (e:Exception){
                Log.d(TAG,e.toString())
            }
        }
        else{
            Toast.makeText(context,R.string.offline_message, Toast.LENGTH_LONG).show()
           // Snackbar.make(view,R.string.offline_message,Snackbar.LENGTH_LONG).show()
        }


    }


}