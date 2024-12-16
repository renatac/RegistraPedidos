package br.com.lucramaisagenciadigital.registrapedidos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.lucramaisagenciadigital.registrapedidos.views.main.MainScreen
import br.com.lucramaisagenciadigital.registrapedidos.views.makesale.MakeSaleScreen

@Composable
fun NavHost(
    navHostController: NavHostController
) {
    androidx.navigation.compose.NavHost(
        navController = navHostController,
        startDestination = ScreenRoutes.MAIN.name
    ) {
        composable(route = ScreenRoutes.MAIN.name) {
            MainScreen(Modifier, navController = navHostController)
        }
        composable(route = ScreenRoutes.MAKING_SALE.name) {
            MakeSaleScreen(Modifier, navController = navHostController)
        }
    }
}
