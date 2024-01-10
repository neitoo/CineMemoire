package com.neito.cinememoire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.neito.cinememoire.ui.theme.CineMemoireTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.animation.doOnEnd
import com.neito.cinememoire.data.NavItem
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreenViewProvider
import com.neito.cinememoire.model.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply{
            setOnExitAnimationListener{ screen ->
                animateSplashScreenExit(screen)

            }
        }
        setContent {
            CineMemoireTheme {
                HomeScreen(
                    onNavItemClicked = { index ->
                        viewModel.onNavItemClicked(index)
                    },
                    selectedItemIndex = viewModel.selectedItemIndex
                )
            }
        }
    }

    private fun animateSplashScreenExit(screen: SplashScreenViewProvider) {
        val zoomX = ObjectAnimator.ofFloat(
            screen.iconView,
            View.SCALE_X,
            0.4f,
            0.0f
        )
        zoomX.interpolator = OvershootInterpolator()
        zoomX.duration = 500L
        zoomX.doOnEnd { screen.remove() }

        val zoomY = ObjectAnimator.ofFloat(
            screen.iconView,
            View.SCALE_Y,
            0.4f,
            0.0f
        )
        zoomY.interpolator = OvershootInterpolator()
        zoomY.duration = 500L
        zoomY.doOnEnd { screen.remove() }

        zoomX.start()
        zoomY.start()
    }

    @Composable
    private fun HomeScreen(onNavItemClicked: (Int) -> Unit, selectedItemIndex: Int) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerContent = {
                    DrawerContent(
                        itemMenu = viewModel.itemMenu,
                        selectedItemIndex = selectedItemIndex,
                        onNavItemClicked = onNavItemClicked,
                        scope = scope,
                        drawerState = drawerState
                    )
                },
                drawerState = drawerState
            ) {
                Scaffold(
                    topBar = {
                        TopAppBarContent(
                            onMenuIconClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            title = viewModel.itemMenu[selectedItemIndex].title
                        )
                    },
                    content = { innerPadding ->
                        BodyContent(innerPadding = innerPadding)
                    }
                )
            }
        }
    }

    @Composable
    private fun DrawerContent(
        itemMenu: List<NavItem>,
        selectedItemIndex: Int,
        onNavItemClicked: (Int) -> Unit,
        scope: CoroutineScope,
        drawerState: DrawerState
    ) {
        ModalDrawerSheet (
            modifier = Modifier.padding(end = 50.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            itemMenu.forEachIndexed { index, navItem ->
                NavigationDrawerItem(
                    label = {
                        Text(text = navItem.title)
                    },
                    selected = index == selectedItemIndex,
                    onClick = {
                        onNavItemClicked(index)
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(navItem.selectedIcon),
                            contentDescription = navItem.title,
                        )
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }

    @Composable
    private fun TopAppBarContent(onMenuIconClick: () -> Unit, title: String) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onMenuIconClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.a_search_icon),
                        contentDescription = "Localized description"
                    )
                }
            }
        )
    }

    @Composable
    private fun BodyContent(innerPadding: PaddingValues) {
        Text(
            text = "Body content",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize()
        )
    }

}
