package com.neito.cinememoire.navigation

import com.neito.cinememoire.R

data class NavItem(
    val title: Int,
    val icon: Int,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        title = R.string.home_name,
        icon = R.drawable.a_home_icon,
        route = Screens.HomeScreen.screen
    ),
    NavItem(
        title = R.string.search_name,
        icon = R.drawable.a_search_icon,
        route = Screens.SearchScreen.screen
    ),
    NavItem(
        title = R.string.wishlist_name,
        icon = R.drawable.a_wishlist_icon,
        route = Screens.WishlistScreen.screen
    ),
)