package com.aksstore.storily.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aksstore.storily.domain.DictionaryRepository
import com.aksstore.storily.model.dictionary.DictionaryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadStoriesViewModel @Inject constructor(private val repository: DictionaryRepository) : ViewModel() {

    private val _searchResult = MutableStateFlow(DictionaryResponse())
    val searchResult: StateFlow<DictionaryResponse> = _searchResult

    fun getSearchResultBySearchTerm(searchTerm : String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSearchResultBySearchTerm(searchTerm).let {
                _searchResult.value = it
            }
        }
    }
}