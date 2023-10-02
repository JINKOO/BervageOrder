package com.example.bervageorder

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bervageorder.presentation.intro.IntroMainScreen
import com.example.bervageorder.presentation.menulist.MenuListMainScreen
import com.example.bervageorder.presentation.menulist.MenuListViewModel


@Composable
fun BeverageOrderNavGraph(
    // TODO Composable에서 항상 Modifier를 default 파라미터 형식으로 정의해야 하는지
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = BeverageOrderDestination.INTRO_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
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
            val viewModel = hiltViewModel<MenuListViewModel>()
            MenuListMainScreen(
                viewModel = viewModel,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}