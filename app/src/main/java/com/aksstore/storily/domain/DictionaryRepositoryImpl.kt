package com.aksstore.storily.domain

import com.aksstore.storily.model.dictionary.DictionaryResponse
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(private val dictionaryService: DictionaryService) : DictionaryRepository {
    override suspend fun getSearchResultBySearchTerm(searchTerm: String): DictionaryResponse {
        return dictionaryService.getSearchResultById(searchTerm)

    }


}