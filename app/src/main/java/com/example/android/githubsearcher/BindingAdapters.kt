package com.example.android.githubsearcher

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubsearcher.network.SearchResult
import com.example.android.githubsearcher.results.ResultsAdapter

@BindingAdapter("setRecyclerData")
fun setRecyclerViewData(recyclerView: RecyclerView, data: List<SearchResult>?) {
    val adapter = recyclerView.adapter as ResultsAdapter
    adapter.submitList(data)
}