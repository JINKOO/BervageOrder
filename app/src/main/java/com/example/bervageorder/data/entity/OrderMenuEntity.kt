package com.example.bervageorder.data.entity

data class OrderMenuEntity(
    val menu: MenuEntity? = null,
    val optionList: List<String>? = null
)