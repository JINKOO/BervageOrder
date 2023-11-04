package com.example.bervageorder.domain.model

/**
 *  현재 선택한 메뉴에 대한 Option 선택 DTO
 */
data class OrderMenuOption(
    val id: String? = null,
    val name: String = "",
    val price: Int = 0,
    val temperature: Temperature = Temperature.NONE,
    val caffeine: Caffeine = Caffeine.NONE,
    val iceQuantity: IceQuantity = IceQuantity.NONE
) {
    fun getOptionFormatString(): String {
        val optionList = mutableListOf<String>()

        temperature.value.let {
            if (it.isNotEmpty()) optionList.add(it)
        }

        caffeine.value.let {
            if (it.isNotEmpty()) optionList.add(it)
        }

        iceQuantity.value.let {
            if (it.isNotEmpty()) optionList.add(it)
        }

        return optionList.joinToString("/")
    }

    fun getPriceFormatString(): String {
        return price.toDecimalFormat()
    }
}