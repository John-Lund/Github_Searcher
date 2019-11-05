package com.example.android.githubsearcher.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.githubsearcher.network.ReadMeApi
import com.example.android.githubsearcher.network.SearchResult

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

import com.example.android.githubsearcher.network.extractReadMe

class DetailsViewModel(searchResult: SearchResult, application: Application) :
    AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _selectedResult = MutableLiveData<SearchResult>()
    val selectedResult: LiveData<SearchResult>
        get() = _selectedResult

    private val _readMeText = MutableLiveData<String>()
    val readMeText: LiveData<String>
        get() = _readMeText

    init {
        _selectedResult.value = searchResult
        getReadMe(searchResult.owner, searchResult.title)
    }

    private fun getReadMe(user: String, title: String) {
        coroutineScope.launch {
            lateinit var resultString: String
            val getResponseDeferred = ReadMeApi.retrofitReadMeService.getReadMeAsync(user, title)
            try {
                resultString = getResponseDeferred.await()
                _readMeText.value = extractReadMe(resultString)


            } catch (e: Exception) {
                Log.e("Result", "Failed" + e)
                _readMeText.value = "no readme found"
            }

        }
    }
}