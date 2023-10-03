package com.example.bervageorder.navigation

import com.example.bervageorder.navigation.BeverageOrderDestinationArg.MENU_ID_ARG
import com.example.bervageorder.navigation.BeverageOrderScreen.INTRO_SCREEN
import com.example.bervageorder.navigation.BeverageOrderScreen.MENU_LIST_SCREEN
import com.example.bervageorder.navigation.BeverageOrderScreen.MENU_DETAIL_SCREEN
import com.example.bervageorder.navigation.BeverageOrderScreen.MENU_ORDER_SCREEN

object BeverageOrderScreen {
    const val INTRO_SCREEN = "intro"
    const val MENU_LIST_SCREEN = "menuList"
    const val MENU_DETAIL_SCREEN = "menuDetail"
    const val MENU_ORDER_SCREEN = "order"
}

object BeverageOrderDestinationArg {
    const val MENU_ID_ARG = "id"
}

object BeverageOrderDestination {
    const val INTRO_ROUTE = "$INTRO_SCREEN"
    const val MENU_LIST_ROUTE = "$MENU_LIST_SCREEN"
    const val MENU_DETAIL_ROUTE = "$MENU_DETAIL_SCREEN/{$MENU_ID_ARG}"
    const val MENU_ORDER_ROUTE = "${MENU_ORDER_SCREEN}/{$MENU_ID_ARG}"
}