package br.com.lucramaisagenciadigital.registrapedidos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.mainscreen.MainScreen
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.AddSaleScreen
import androidx.navigation.compose.NavHost

@Composable
fun NavHost(
    viewModel: UserDataViewModel,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenRoutes.MAIN.name
    ) {
        composable(route = ScreenRoutes.MAIN.name) {
            MainScreen(Modifier, navController = navHostController)
        }
        composable(route = ScreenRoutes.MAKING_SALE.name) {
            AddSaleScreen(
                Modifier,
                viewModel,
                navigateToMainScreen = {
                    navHostController.navigate(ScreenRoutes.MAIN.name)
                }
            )
        }
    }
}
