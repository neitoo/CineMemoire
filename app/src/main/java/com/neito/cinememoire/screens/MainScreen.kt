package com.neito.cinememoire.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.neito.cinememoire.R
import com.neito.cinememoire.navigation.Screens
import com.neito.cinememoire.navigation.listOfNavItems
import com.neito.cinememoire.presentation.BottomSheetContent
import com.neito.cinememoire.presentation.BottomSheetViewModel
import com.neito.cinememoire.presentation.componentes.CreationContent
import com.neito.cinememoire.presentation.componentes.SettingsContent
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController){
    val sheetState = rememberModalBottomSheetState()
    val viewModel = koinInject<BottomSheetViewModel>()

    Scaffold(
        topBar = {
            TopAppBarContent(viewModel)
        },
        bottomBar = {
            BottomBarContent(navController)
        }
    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.inverseOnSurface,
        ) {
            NavGraph(navController,viewModel)
        }
    }

    if (viewModel.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.showBottomSheet = false
                viewModel.bottomSheetContent = null },
            sheetState = sheetState
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ){
                viewModel.bottomSheetContent?.let { content ->
                    when (content) {
                        is BottomSheetContent.SettingContent -> {
                            SettingsContent()
                        }
                        is BottomSheetContent.CreateContent -> {
                            CreationContent()
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun NavGraph(
    navController: NavHostController,
    viewModel: BottomSheetViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.screen,
    ) {
        composable(route = Screens.HomeScreen.screen) {
            HomeScreen()
        }
        composable(route = Screens.SearchScreen.screen) {
            SearchScreen(navController, viewModel)
        }
        composable(route = Screens.WishlistScreen.screen) {
            WishlistScreen()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopAppBarContent(
    viewModel: BottomSheetViewModel
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Left
            )
        },
        actions = {
            IconButton(
                onClick = {
                    viewModel.showBottomSheet = true
                    viewModel.bottomSheetContent = BottomSheetContent.SettingContent
                }
            ) {
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

