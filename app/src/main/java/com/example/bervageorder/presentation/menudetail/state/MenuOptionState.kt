package com.example.bervageorder.presentation.menudetail.state

import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.bervageorder.R
import com.example.bervageorder.domain.model.OptionType

sealed class MenuOptionState(
    val type: OptionType,
    @ColorRes val selectedTextColor: Color,
    @ColorRes val unSelectedTextColor: Color,
    @ColorRes val selectedBackGroundColor: Color,
    @ColorRes val unSelectedBackGroundColor: Color
) {
    object Hot : MenuOptionState(
        type = OptionType.HOT,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object Ice : MenuOptionState(
        type = OptionType.ICE,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object Caffeine: MenuOptionState(
        type = OptionType.CAFFEINE,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object DeCaffeine: MenuOptionState(
        type = OptionType.DE_CAFFEINE,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object LessIce: MenuOptionState(
        type = OptionType.ICE_LESS,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object NormalIce: MenuOptionState(
        type = OptionType.ICE_NORMAL,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object MoreIce: MenuOptionState(
        type = OptionType.ICE_MORE,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )

    companion object {
        fun getDefaultOptionList(): List<MenuOptionState> {
            return listOf(Hot, Ice)
        }
        fun getCaffeineOptionList(): List<MenuOptionState> {
            return listOf(DeCaffeine, Caffeine)
        }
        fun getIceQuantityOptionList(): List<MenuOptionState> {
            return listOf(LessIce, NormalIce, MoreIce)
        }
    }
}

@Composable
fun MenuOptionState.getOptionStringResId(): Int {
    return when(type) {
        OptionType.HOT -> R.string.button_hot
        OptionType.ICE -> R.string.button_ice
        OptionType.CAFFEINE -> R.string.button_caffeine
        OptionType.DE_CAFFEINE -> R.string.button_decaffeine
        OptionType.ICE_LESS -> R.string.button_ice_less
        OptionType.ICE_NORMAL -> R.string.button_ice_normal
        OptionType.ICE_MORE -> R.string.button_ice_more
    }
}