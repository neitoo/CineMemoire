package com.neito.cinememoire.navigation
sealed class Screens(val screen: String){
    data object HomeScreen: Screens("home")
    data object SearchScreen: Screens("search")
    data object WishlistScreen: Screens("wishlist")
    data object SettingsScreen: Screens("settings")
    data object MainScreen: Screens("main");
    data object CreateScreen: Screens("create")
}