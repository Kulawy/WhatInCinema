package com.gerapp.whatincinema.base

import com.gerapp.whatincinema.base.Route.Home
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class RouteTest {

    @Test
    fun `Check that if Home Route is Home Screen route`() {
        val routeHome = Home

        Assertions.assertEquals(routeHome.route, HOME_SCREEN)
    }

    @Test
    fun `Check that if createRoute with passed movieId creates valid route`() {
        val movieId = 123
        val routeDetailPath = Route.Detail.createRoute(123)

        Assertions.assertEquals(routeDetailPath, "$DETAIL_SCREEN/$movieId")
    }
}
