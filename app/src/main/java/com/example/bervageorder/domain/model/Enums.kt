package com.example.bervageorder.domain.model

enum class OptionType{

}

enum class Temperature {
    ICE,
    HOT,
    NONE
}

enum class Caffeine {
    CAFFEINE,
    DE_CAFFEINE,
    NONE
}

enum class IceQuantity {
    LESS,
    NORMAL,
    MORE,
    NONE
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