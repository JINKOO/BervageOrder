package com.example.bervageorder.domain.model

enum class OptionType{

}

enum class Temperature(val value: String) {
    ICE("아이스"),
    HOT("뜨겁게"),
    NONE("")
}

enum class Caffeine(val value: String) {
    CAFFEINE("카페인"),
    DE_CAFFEINE("디카페인"),
    NONE("")
}

enum class IceQuantity(val value: String) {
    LESS("적게"),
    NORMAL("보통"),
    MORE("많이"),
    NONE("")
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