package com.example.android.githubsearcher.network
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SearchResult (
    val id: Int,
    val title: String,
    val owner: String,
    val forks: Int,
    val openIssues: Int,
    val watchers: Int
) : Parcelable