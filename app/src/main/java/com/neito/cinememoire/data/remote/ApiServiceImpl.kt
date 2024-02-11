package com.neito.cinememoire.data.remote

import android.util.Log
import com.neito.cinememoire.BuildConfig
import com.neito.cinememoire.data.remote.dto.DetailsResponse
import com.neito.cinememoire.data.remote.dto.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.Headers
import io.ktor.http.Parameters
import io.ktor.http.headers
import io.ktor.http.parameters
import kotlinx.serialization.json.Json

class ApiServiceImpl(
    private val client: HttpClient
): ApiService {
    override suspend fun getSearch(keyword: String, page: String): SearchResponse? {
        val response = client.get(HttpRoutes.GET_SEARCH) {
            headers{
                append("X-API-KEY", "1b4b7620-8888-4533-8928-3b9c54216f4d")
                append("Content-Type", "application/json")
            }
            parameter("keyword", keyword)
            parameter("page", page)
        }
        Log.d("resp1", "${response.bodyAsText()}")

        return try {
            val responseBody = response.bodyAsText()
            Json.decodeFromString(responseBody)
        } catch(e: RedirectResponseException) {
            // 3xx
            Log.d("srch","Ошибка: ${e.response.status.description}")
            null
        } catch(e: ClientRequestException) {
            // 4xx
            Log.d("srch","Error: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            // 5xx
            Log.d("srch","Ошибка: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            Log.d("srch","Ошибка: ${e.message}")
            null
        }
    }

    override suspend fun getDetail(id: Long): DetailsResponse? {
        val response = client.get {
            url("${HttpRoutes.GET_DETAIL}${id}")
            header("X-API-KEY", BuildConfig.API_TMDB)
        }
        return response.body()
    }
}