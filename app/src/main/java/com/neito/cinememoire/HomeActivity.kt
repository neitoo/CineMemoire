package com.neito.cinememoire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.neito.cinememoire.ui.theme.CineMemoireTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.navigation.compose.rememberNavController
import com.neito.cinememoire.navigation.MainNavGraph


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply{
            setOnExitAnimationListener{ screen ->
                animateSplashScreenExit(screen)
            }
        }
        setContent {
            CineMemoireTheme {
                val mainNavController = rememberNavController()

                MainNavGraph(navController = mainNavController)

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

}
