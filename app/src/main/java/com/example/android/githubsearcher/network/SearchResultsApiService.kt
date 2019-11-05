package com.example.android.githubsearcher.network

import android.util.Base64
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.UnsupportedEncodingException

private const val BASE_URL = "https://api.github.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface SearchResultsApiService {
    @GET("search/repositories")
    fun getSearchResultsAsync(@Query("q") keywords: String):
            Deferred<String>
}

object SearchResultsApi {
    val retrofitSearchService: SearchResultsApiService by lazy { retrofit.create(SearchResultsApiService::class.java) }
}

interface ReadMeApiService {
    @GET("repos/{user}/{title}/readme")
    fun getReadMeAsync(
        @Path("user") user: String,
        @Path("title") title: String):
            Deferred<String>
}

object ReadMeApi{
    val retrofitReadMeService: ReadMeApiService by lazy { retrofit.create(ReadMeApiService::class.java) }
}


fun convertStringToResults(response: String): ArrayList<SearchResult> {
    val resultsList = arrayListOf<SearchResult>()
    val jsonObject = JSONObject(response)
    val jsonArray = jsonObject.optJSONArray("items")
    if (jsonArray.length() == 0) {
        return resultsList
    }
    for (index in 0 until jsonArray.length()) {
        val result: JSONObject? = jsonArray.optJSONObject(index)
        if (null != result) {
            val id = result.optInt("id", 0)
            val title = result.optString("name", "no name")
            val ownerObject = result.optJSONObject("owner")
            var owner = "unknown"
            if (null != ownerObject) {
                owner = ownerObject.optString("login", "unknown")
            }
            val forks = result.optInt("forks", 0)
            val openIssues = result.optInt("open_issues_count", 0)
            val watchers= result.optInt("watchers", 0)

            resultsList.add(SearchResult(id, title, owner, forks, openIssues, watchers))
        }
    }
    return resultsList
}

fun extractReadMe(response: String): String{
    val jsonObject = JSONObject(response)
    val contentString = jsonObject.optString("content", "no readme found")
    if(contentString == "no readme found"){
        return contentString
    }
    val utfString: ByteArray
    try {
        utfString = Base64.decode(contentString.toByteArray(charset("UTF-8")), Base64.DEFAULT)
    } catch (e: UnsupportedEncodingException) {
        return "unable to read content"
    }
    return String(utfString)
}


