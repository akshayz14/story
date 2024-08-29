package com.aksstore.storily.domain

import com.aksstore.storily.base.NetworkResult
import com.aksstore.storily.model.dictionary.DictionaryResponse
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(private val dictionaryService: DictionaryService) : DictionaryRepository {
    override suspend fun getSearchResultBySearchTerm(searchTerm: String): NetworkResult<DictionaryResponse> {
        return try {
            val response = dictionaryService.getSearchResultById(searchTerm)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Exception: ${e.message}")
        }
    }


}