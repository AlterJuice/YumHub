package com.alterjuice.navigation.di

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.navigation.NavigationManager
import org.koin.dsl.module

object NavigationDI: DIModulesHub {

    override fun modules() = arrayOf(
        navigationModule()
    )

    private fun navigationModule() = module {
        single<NavigationManager> {
            NavigationManager()
        }
    }
}