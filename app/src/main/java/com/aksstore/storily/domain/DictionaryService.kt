package com.aksstore.storily.domain

import com.aksstore.storily.model.dictionary.DictionaryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryService {

    @GET("api/v2/entries/en/{searchTerm}")
    suspend fun getSearchResultById(@Path("searchTerm") searchTerm : String): DictionaryResponse

}