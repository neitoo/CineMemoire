package com.neito.cinememoire.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.neito.cinememoire.R
import com.neito.cinememoire.data.NavItem

class HomeViewModel: ViewModel() {
    var selectedItemIndex by mutableStateOf(0)

    val itemMenu = listOf(
        NavItem(
            title = "Главная",
            selectedIcon = R.drawable.a_home_icon,
        ),
        NavItem(
            title = "Вишлист",
            selectedIcon = R.drawable.a_wishlist_icon,
        ),
        NavItem(
            title = "Настройки",
            selectedIcon = R.drawable.a_settings_icon,
        ),
    )

    fun onNavItemClicked(index: Int) {
        selectedItemIndex = index
    }
}