package com.alterjuice.data.other


import com.google.gson.Gson
import com.google.gson.GsonBuilder

object YumHubGson {

    fun generateGson(): Gson {
        return GsonBuilder()

            .create()
    }


}
