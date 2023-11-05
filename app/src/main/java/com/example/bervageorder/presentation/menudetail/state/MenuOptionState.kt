package com.example.bervageorder.presentation.menudetail.state

import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.bervageorder.R
import com.example.bervageorder.domain.model.OptionTypeSealed

sealed class MenuOptionState(
    val type: OptionTypeSealed,
    @ColorRes val selectedTextColor: Color,
    @ColorRes val unSelectedTextColor: Color,
    @ColorRes val selectedBackGroundColor: Color,
    @ColorRes val unSelectedBackGroundColor: Color
) {
    object Hot : MenuOptionState(
        type = OptionTypeSealed.Hot,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object Ice : MenuOptionState(
        type = OptionTypeSealed.Ice,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object Caffeine: MenuOptionState(
        type = OptionTypeSealed.Caffeine,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object DeCaffeine: MenuOptionState(
        type = OptionTypeSealed.DeCaffeine,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object LessIce: MenuOptionState(
        type = OptionTypeSealed.IceLess,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object NormalIce: MenuOptionState(
        type = OptionTypeSealed.IceNormal,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray
    )
    object MoreIce: MenuOptionState(
        type = OptionTypeSealed.IceMore,
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
        OptionTypeSealed.Hot -> R.string.button_hot
        OptionTypeSealed.Ice -> R.string.button_ice
        OptionTypeSealed.Caffeine -> R.string.button_caffeine
        OptionTypeSealed.DeCaffeine -> R.string.button_decaffeine
        OptionTypeSealed.IceLess -> R.string.button_ice_less
        OptionTypeSealed.IceNormal -> R.string.button_ice_normal
        OptionTypeSealed.IceMore -> R.string.button_ice_more
    }
}