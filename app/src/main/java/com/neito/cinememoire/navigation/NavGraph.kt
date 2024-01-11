package com.neito.cinememoire.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neito.cinememoire.screens.HomeScreen
import com.neito.cinememoire.screens.MainScreen
import com.neito.cinememoire.screens.SearchScreen
import com.neito.cinememoire.screens.SettingsScreen
import com.neito.cinememoire.screens.WishlistScreen

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.name,
    ){
        composable(route = Screens.HomeScreen.name){
            HomeScreen()
        }
        composable(route = Screens.SearchScreen.name){
            SearchScreen()
        }
        composable(route = Screens.WishlistScreen.name){
            WishlistScreen()
        }
    }
}

@Composable
fun MainNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.name,
    ){
        composable(route = Screens.MainScreen.name){
            MainScreen(navController)
        }
        composable(route = Screens.SettingsScreen.name){
            SettingsScreen(navController)
        }
    }
}
