package com.example.android.githubsearcher.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.githubsearcher.network.SearchResult

class DetailsViewModelFactory(
    private val searchResult: SearchResult,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(searchResult, application) as T
        }
        throw IllegalArgumentException("Can't create a view model with this class")
    }

}