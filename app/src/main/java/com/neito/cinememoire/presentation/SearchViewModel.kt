package com.neito.cinememoire.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neito.cinememoire.data.remote.ApiService
import com.neito.cinememoire.data.remote.dto.SearchResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val apiService: ApiService) : ViewModel() {
    var textState by mutableStateOf("")
    var previousSearchText by mutableStateOf("")

    private val _searchResults = MutableStateFlow<SearchResponse?>(null)
    val searchResults: StateFlow<SearchResponse?> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun performSearch(keyword: String, page: String){
        _isLoading.value = true
        viewModelScope.launch{
            _searchResults.value = apiService.getSearch(keyword,page)
            _isLoading.value = false
        }
    }

    fun clearSearchResults() {
        _searchResults.value = null
    }
}