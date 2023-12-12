package com.alterjuice.data.other


import com.alterjuice.android_utils.RuntimeTypeAdapter
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutritionAttr
import com.alterjuice.domain.model.nutrition.NutritionAttrData
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

object YumHubGson {

    fun generateGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapterFactory(getYumHubMealAadapter())
            .registerTypeAdapter(NutritionAttr::class.java, NutritionAttrDeserializer())
            .create()
    }

    fun getYumHubMealAadapter() = RuntimeTypeAdapter.of(YumHubMeal::class.java, "type")
        .registerSubtype(YumHubMeal.NutritionIXMeal::class.java, "nutritionix")
        .registerSubtype(YumHubMeal.TheMealDBItem::class.java, "local_database")



}


internal class NutritionAttrDeserializer : JsonDeserializer<NutritionAttr> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): NutritionAttr? {
        println("${json?.toString()}")
        if (json == null)
            return null
        if (json.isJsonObject)
            return context.deserialize(json, NutritionAttrData::class.java)
        else
            return NutritionEnum.valueOf(json.asString)

    }
}