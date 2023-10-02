package com.example.bervageorder

import com.example.bervageorder.BeverageOrderDestinationArg.MENU_ID_ARG
import com.example.bervageorder.BeverageOrderScreen.INTRO_SCREEN
import com.example.bervageorder.BeverageOrderScreen.MENU_LIST_SCREEN
import com.example.bervageorder.BeverageOrderScreen.ORDER_DETAIL_SCREEN

object BeverageOrderScreen {
    const val INTRO_SCREEN = "intro"
    const val MENU_LIST_SCREEN = "menuList"
    const val ORDER_DETAIL_SCREEN = "orderDetail"
}

object BeverageOrderDestinationArg {
    const val MENU_ID_ARG = "id"
}

object BeverageOrderDestination {
    const val INTRO_ROUTE = "$INTRO_SCREEN"
    const val MENU_LIST_ROUTE = "$MENU_LIST_SCREEN"
    const val ORDER_DETAIL_ROUTE = "$ORDER_DETAIL_SCREEN/{$MENU_ID_ARG}"
}