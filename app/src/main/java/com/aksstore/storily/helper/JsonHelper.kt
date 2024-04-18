import android.content.Context
import com.aksstore.storily.model.StoryModel
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader

object JsonHelper {
    fun parseJson(context: Context, fileName: String): StoryModel? {
        val gson = Gson()
        try {
            context.assets.open(fileName).use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    return gson.fromJson(reader, StoryModel::class.java)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}