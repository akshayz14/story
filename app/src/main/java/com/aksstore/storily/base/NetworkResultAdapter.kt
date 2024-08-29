package com.aksstore.storily.base

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class NetworkResultAdapter<T : Any>(
    private val successType: Type
) : JsonDeserializer<NetworkResult<T>> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): NetworkResult<T> {
        val jsonObject = json.asJsonObject

        return when {
            jsonObject.has("data") -> {
                val data = context.deserialize<T>(jsonObject.get("data"), successType)
                NetworkResult.Success(data)
            }
            jsonObject.has("exception") -> {
                val exception = context.deserialize<Exception>(jsonObject.get("exception"), Exception::class.java)
                NetworkResult.Error("exception")
            }
            jsonObject.has("loading") -> {
                NetworkResult.Loading
            }
            else -> throw JsonParseException("Unknown type")
        }
    }
}