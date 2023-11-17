package com.alterjuice.database

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.database.messages.MessagesDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object DatabaseDI: DIModulesHub {
    override fun modules() = arrayOf(
        databaseModule()
    )

    private fun databaseModule() = module {
        single<YumHubDatabase> {
            createYumHubDatabase(androidApplication())
        }
        single<MessagesDao> {
            get<YumHubDatabase>().messagesEntities()
        }
    }
}