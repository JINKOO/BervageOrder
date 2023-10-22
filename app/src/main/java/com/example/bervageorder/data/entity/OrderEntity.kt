package com.example.bervageorder.data.entity

data class OrderEntity(
    val id: String,
    val name: String,
    val price: Int,
    val type: MenuType,
    val temperature: TemperatureType,
    val caffeine: CaffeineType,
    val iceQuantity: IceQuantityType,
) {
    fun toDesireString(): String {
        val ret = mutableListOf<String>()

        temperature.toDesireString()?.let {
            ret.add(it)
        }

        caffeine.toDesireString()?.let {
            ret.add(it)
        }

        iceQuantity.toDesireString()?.let {
            ret.add(it)
        }

        return ret.joinToString("/")
    }
}

enum class IceQuantityType {
    NONE,
    REGULAR,
    LESS,
    MORE,
    ;

    fun toDesireString(): String? {
        return when (this) {
            REGULAR -> "보통"
            LESS -> "적게"
            MORE -> "많이"
            else -> null
        }
    }
}
