package com.example.bervageorder.domain.model

enum class Temperature {
    ICE,
    HOT,
    NONE;

    fun getName(): String? = when(this) {
        ICE -> "아이스"
        HOT -> "뜨겁게"
        else -> null
    }
}

enum class Caffeine {
    CAFFEINE,
    DE_CAFFEINE,
    NONE;

    fun getName(): String? = when(this) {
        CAFFEINE -> "카페인"
        DE_CAFFEINE -> "디카페인"
        else -> null
    }
}

enum class IceQuantity {
    LESS,
    NORMAL,
    MORE,
    NONE;

    fun getName(): String? = when(this) {
        LESS -> "적게"
        NORMAL -> "보통"
        MORE -> "많이"
        else -> null
    }
}

sealed class OptionTypeSealed {
    object Hot : OptionTypeSealed()
    object Ice : OptionTypeSealed()
    object Caffeine : OptionTypeSealed()
    object DeCaffeine : OptionTypeSealed()
    object IceLess : OptionTypeSealed()
    object IceNormal : OptionTypeSealed()
    object IceMore : OptionTypeSealed()
}