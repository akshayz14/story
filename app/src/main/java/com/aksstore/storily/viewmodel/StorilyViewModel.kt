package com.aksstore.storily.viewmodel

import JsonHelper
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.aksstore.storily.model.StoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
@HiltViewModel
class StorilyViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {

    fun loadData(fileName: String): StoryModel? {
        return JsonHelper.parseJson(context, fileName)
    }

}