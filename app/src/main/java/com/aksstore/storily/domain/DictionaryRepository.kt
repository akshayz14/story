package com.aksstore.storily.domain

import com.aksstore.storily.model.dictionary.DictionaryResponse

interface DictionaryRepository {
    suspend fun getSearchResultBySearchTerm(searchTerm : String) : DictionaryResponse
}