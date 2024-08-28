package com.aksstore.storily.model.dictionary

data class Phonetic(
    val audio: String,
    val license: License,
    val sourceUrl: String,
    val text: String
)