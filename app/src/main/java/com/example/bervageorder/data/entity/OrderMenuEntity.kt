package com.example.bervageorder.data.entity

import com.example.bervageorder.domain.model.OptionType

data class OrderMenuEntity(
    val menu: MenuEntity? = null,
    val optionList: List<OptionType>? = null
)