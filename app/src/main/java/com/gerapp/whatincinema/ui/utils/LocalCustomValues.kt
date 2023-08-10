package com.gerapp.whatincinema.ui.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDim = compositionLocalOf { Dimensions() }
val LocalAlpha = compositionLocalOf { Alphas() }
val LocalDrawOrder = compositionLocalOf { DrawOrders() }
val LocalAnim = compositionLocalOf { AnimationProperties() }

data class Dimensions(
    val zero: Dp = 0.dp,
    val spaceXXSmall: Dp = 2.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,

    val favouritesButtonSize: Dp = 64.dp,
    val favouritesIconSize: Dp = favouritesButtonSize.div(1.2F),
    val favouritesListButtonSize: Dp = 64.dp,
)

data class Alphas(
    val transparent25: Float = 0.25F,
    val transparent50: Float = 0.5F,
    val transparent75: Float = 0.75F,
)

data class DrawOrders(
    val onFront: Float = 1.0F,
    val onVeryFront: Float = 10.0F,
)

data class AnimationProperties(
    val shortDuration: Int = 200,
    val normalDuration: Int = 500,
    val longDuration: Int = 1000,
    val veryLongDuration: Int = 3000,
    val quoterRotation: Float = 90.0F,
    val zeroRotation: Float = 0.0F,
)
