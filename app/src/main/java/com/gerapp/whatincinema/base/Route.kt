package com.gerapp.whatincinema.base

const val HOME_SCREEN = "home"
const val DETAIL_SCREEN = "detail"

sealed class Route(val route: String) {
    object Home : Route(HOME_SCREEN)
    object Detail : Route("$DETAIL_SCREEN/{movieId}") {
        fun createRoute(movieId: Int) = "$DETAIL_SCREEN/$movieId"
    }
}
