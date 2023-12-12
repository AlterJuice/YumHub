package com.alterjuice.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class NavigationManager constructor() {
    val commandsWithOptionsBuilder = MutableSharedFlow<Pair<NavCommand, (NavOptionsBuilder.() -> Unit)?>>(replay = 0)
    val commandsWithArgs = MutableSharedFlow<String>(replay = 0)

    val showNoInternetConnectionSheet = MutableSharedFlow<Unit>(replay = 0)
    val hideNoInternetConnectionSheet = MutableSharedFlow<Unit>(replay = 0)

    val savedDestinations = mutableSetOf<String>()

    fun navigate(directions: NavCommand) = CoroutineScope(Main).launch {
        commandsWithOptionsBuilder.emit(directions to null)
    }
    fun navigate(directions: NavCommand, navOptionsBuilder: NavOptionsBuilder.() -> Unit) = CoroutineScope(Main).launch {
        commandsWithOptionsBuilder.emit(directions to navOptionsBuilder)
    }

    fun navigateWithArgs(direction: String) = CoroutineScope(Main).launch {
        commandsWithArgs.emit(direction)
    }


    fun setNoInternetConnectionSheetVisibility(isVisible: Boolean) = CoroutineScope(Main).launch {
        if (isVisible) {
            showNoInternetConnectionSheet.emit(Unit)
        } else {
            hideNoInternetConnectionSheet.emit(Unit)
        }
    }
}