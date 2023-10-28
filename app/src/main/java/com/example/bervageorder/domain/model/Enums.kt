package com.example.bervageorder.domain.model

enum class OptionType(val value: String) {
    HOT("뜨겁게"),
    ICE("아이스"),
    CAFFEINE("카페인"),
    DE_CAFFEINE("디카페인"),
    ICE_LESS("얼음 적게"),
    ICE_NORMAL("얼음 보통"),
    ICE_MORE("얼음 많이")
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
}
