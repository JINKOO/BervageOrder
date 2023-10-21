package com.example.bervageorder.domain.model

import com.example.bervageorder.data.entity.OrderMenuEntity

data class OrderMenu(
    val menu: Menu = Menu(),
    val optionList: List<OptionType> = emptyList()
) {
    companion object {
        operator fun invoke(orderMenuEntity: OrderMenuEntity): OrderMenu {
            return OrderMenu(
                menu = orderMenuEntity.menu?.let { Menu(it) } ?: Menu(),
                optionList = orderMenuEntity.optionList ?: emptyList()
            )
        }
    }
}