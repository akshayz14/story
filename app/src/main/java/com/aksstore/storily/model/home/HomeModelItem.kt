package com.aksstore.storily.model.home

data class HomeModelItem(
    val extra: String,
    val id: String,
    val name: String,
    val order: Int,
    val story_desc: String,
    val story_image: String,
    val story_title: String
)