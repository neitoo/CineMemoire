package com.neito.cinememoire.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neito.cinememoire.R
import com.neito.cinememoire.navigation.Screens
import com.neito.cinememoire.navigation.listOfNavItems


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController){
    val navControllerBottomBar = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBarContent(navController)
        },
        bottomBar = {
            BottomBarContent(navControllerBottomBar)
        }
    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.inverseOnSurface,
        ) {
            BottomNavGraph(navControllerBottomBar, navController)
        }
    }
}

@Composable
private fun BottomNavGraph(
    navControllerBottomBar: NavHostController,
    navController: NavHostController
) {
    NavHost(
        navController = navControllerBottomBar,
        startDestination = Screens.HomeScreen.screen,
    ) {
        composable(route = Screens.HomeScreen.screen) {
            HomeScreen()
        }
        composable(route = Screens.SearchScreen.screen) {
            SearchScreen(navController)
        }
        composable(route = Screens.WishlistScreen.screen) {
            WishlistScreen()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopAppBarContent(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Left
            )
        },
        actions = {
            IconButton(onClick = { navController.navigate(Screens.SettingsScreen.screen)}) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.a_settings_icon),
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Composable
fun BottomBarContent(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier.height(60.dp),
        tonalElevation = 0.dp
    ) {
        listOfNavItems.forEach { screens ->
            NavigationBarItem(
                /*label = {
                    Text(text = stringResource(screens.title))
                },*/
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(screens.icon),
                        contentDescription = "Nav Icon"
                    )
                },
                selected = currentDestination?.hierarchy?.any{
                    it.route == screens.route
                } == true,
                onClick = {
                    navController.navigate(screens.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

