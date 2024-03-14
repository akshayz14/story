package com.aksstore.storily.model

data class StoryModel(
    var stories: List<Story>
)

data class Story(
    var story_description: String,
    var story_name: String,
    var story_title: String
)