package com.aksstore.storily.domain

import com.aksstore.storily.base.NetworkResult
import com.aksstore.storily.model.dictionary.DictionaryResponse

interface DictionaryRepository {
    suspend fun getSearchResultBySearchTerm(searchTerm : String) : NetworkResult<DictionaryResponse>
}