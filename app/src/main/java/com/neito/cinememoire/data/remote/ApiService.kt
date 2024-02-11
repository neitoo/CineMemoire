package com.neito.cinememoire.data.remote

import com.neito.cinememoire.data.remote.dto.DetailsResponse
import com.neito.cinememoire.data.remote.dto.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.realm.kotlin.log.LogLevel

interface ApiService {

    suspend fun getSearch(keyword: String, page: String): SearchResponse?

    suspend fun getDetail(id: Long): DetailsResponse?

    companion object{
        fun create(): ApiService{
            return ApiServiceImpl(
                client = HttpClient(Android)
            )
        }
    }
}