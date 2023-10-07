package com.example.bervageorder.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bervageorder.navigation.BeverageOrderDestinationArg.MENU_ID_ARG
import com.example.bervageorder.navigation.BeverageOrderScreen.MENU_DETAIL_SCREEN
import com.example.bervageorder.navigation.BeverageOrderScreen.MENU_ORDER_SCREEN
import com.example.bervageorder.presentation.intro.IntroMainScreen
import com.example.bervageorder.presentation.menulist.MenuListMainScreen
import com.example.bervageorder.presentation.menulist.MenuListViewModel
import com.example.bervageorder.presentation.menudetail.MenuDetailMainScreen
import com.example.bervageorder.presentation.menudetail.MenuDetailViewModel
import com.example.bervageorder.presentation.order.OrderMainScreen
import com.example.bervageorder.presentation.order.OrderViewModel


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
                navigateToOrderDetail = {
                    navController.navigate(
                        route = "$MENU_DETAIL_SCREEN/$it"
                    )
                },
                navigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = BeverageOrderDestination.MENU_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(MENU_ID_ARG) { type = NavType.StringType }
            )
        ) {
            val viewModel = hiltViewModel<MenuDetailViewModel>()
            MenuDetailMainScreen(
                viewModel = viewModel,
                navigateUp = { navController.navigateUp() },
                navigateToOrder = { navController.navigate(route = "$MENU_ORDER_SCREEN/$it") }
            )
        }

        composable(
            route = BeverageOrderDestination.MENU_ORDER_ROUTE,
            arguments = listOf(
                navArgument(MENU_ID_ARG) { type = NavType.StringType }
            )
        ) {
            val viewModel = hiltViewModel<OrderViewModel>()
            OrderMainScreen(
                viewModel = viewModel,
                navigateUp = { navController.navigateUp() },
                navigateToIntro = {
                    navController.navigate(BeverageOrderDestination.INTRO_ROUTE) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}