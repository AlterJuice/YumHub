package com.alterjuice.data.di

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.data.other.YumHubGson
import com.google.gson.Gson
import org.koin.dsl.module

object DataDI: DIModulesHub {
    override fun modules() = arrayOf(
        utils()
    )

    private fun utils() = module {
        factory<Gson> {
            YumHubGson.generateGson()
        }
    }
}
