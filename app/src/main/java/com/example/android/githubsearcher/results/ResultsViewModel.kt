package com.example.android.githubsearcher.results

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.android.githubsearcher.network.SearchResult
import com.example.android.githubsearcher.network.SearchResultsApi
import com.example.android.githubsearcher.network.convertStringToResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResultsViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _searchResultList = MutableLiveData<List<SearchResult>>()
    val searchResultList: LiveData<List<SearchResult>>
        get() = _searchResultList

    fun processSearch(searchString: String) {
        var string = searchString
        string = string.trim { it <= ' ' }
        if (string.isEmpty()) {
            return
        }
        string = string.replace("( )+".toRegex(), " ")
        string = string.replace(" ".toRegex(), "+")
        getSearchResults(string)

    }

    private fun getSearchResults(searchString: String) {
        coroutineScope.launch {
            lateinit var resultString: String

            val getResponseDeferred =
                SearchResultsApi.retrofitSearchService.getSearchResultsAsync(searchString)
            try {
                resultString = getResponseDeferred.await()
                val resultsList = convertStringToResults(resultString)
                if (resultsList.isNotEmpty()) {
                    _searchResultList.value = resultsList
                }
            } catch (e: Exception) {
                Log.e("Result", "Failed: " + e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToSelectedResult = MutableLiveData<SearchResult>()
    val navigateToSelectedResult: LiveData<SearchResult>
        get() = _navigateToSelectedResult

    fun displayDetailsfragment(searchResult: SearchResult) {
        _navigateToSelectedResult.value = searchResult
    }

    fun navigateToDetailsFragmentComplete() {
        _navigateToSelectedResult.value = null
    }

}