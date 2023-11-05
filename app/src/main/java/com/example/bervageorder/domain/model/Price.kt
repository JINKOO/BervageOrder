package com.example.bervageorder.domain.model

import java.text.DecimalFormat

@JvmInline
value class Price(private val value: Int) {

    init {
        require(value >= 0) { "Price must be positive" }
    }

    fun toDecimalFormat(): String {
        val decimalFormat = DecimalFormat("#,###")
        return decimalFormat.format(value)
    }

    companion object {
        val ZERO = Price(0)

        fun createDallor(value: Double): Price {
            return Price((value * 1320).toInt())
        }
    }
}
