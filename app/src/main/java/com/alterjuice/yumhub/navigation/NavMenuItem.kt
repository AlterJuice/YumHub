package com.alterjuice.yumhub.navigation

import com.alterjuice.navigation.NavCommand

data class NavMenuItem(
    val name: String,
    var navCommand: NavCommand,
    var icon: Int,
    var badgeCount: Int = 0,
)
