package com.example.android.githubsearcher.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.android.githubsearcher.databinding.ResultsListItemBinding
import com.example.android.githubsearcher.network.SearchResult


class ResultsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<SearchResult, ResultsAdapter.ResultsViewHolder>(DiffCallback) {

    class ResultsViewHolder(private var binding: ResultsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchResult: SearchResult) {
            binding.searchResult = searchResult
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            ResultsListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClickListener.onClick(getItem(position)) }
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class OnClickListener(val clickListener: (searchResult: SearchResult) -> Unit) {
        fun onClick(searchResult: SearchResult) = clickListener(searchResult)
    }

}