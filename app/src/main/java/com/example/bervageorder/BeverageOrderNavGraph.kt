package com.example.bervageorder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bervageorder.presentation.intro.IntroMainScreen
import com.example.bervageorder.presentation.menulist.MenuListMainScreen


@Composable
fun BeverageOrderNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = BeverageOrderDestination.INTRO_ROUTE
    ) {
        composable(
            route = BeverageOrderDestination.INTRO_ROUTE
        ) {
            IntroMainScreen(
                navigateToMenuList = { navController.navigate(BeverageOrderDestination.MENU_LIST_ROUTE) }
            )
        }

        composable(
            route = BeverageOrderDestination.MENU_LIST_ROUTE
        ) {
            MenuListMainScreen()
        }
    }
}