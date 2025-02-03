package br.com.lucramaisagenciadigital.registrapedidos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.mainscreen.MainScreen
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.AddSaleScreen
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.seeaasalesscreen.SeeAllSalesScreen

@Composable
fun NavHost(
    viewModel: UserDataViewModel,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenRoutes.FIRST_SCREEN_ROUTE.name
    ) {
        composable(route = ScreenRoutes.FIRST_SCREEN_ROUTE.name) {
            MainScreen(
                Modifier,
                navigateToAddSaleScreen = {
                    navHostController.navigate(ScreenRoutes.ADD_SALE_ROUTE.name)
                },
                navigateToSeeAllSalesScreen = {
                    navHostController.navigate(ScreenRoutes.SEE_ALL_SALES_ROUTE.name)
                })
        }
        composable(route = ScreenRoutes.ADD_SALE_ROUTE.name) {
            AddSaleScreen(
                Modifier,
                viewModel,
                navigateToMainScreen = {
                    navHostController.popBackStack()
                }
            )
        }
        composable(route = ScreenRoutes.SEE_ALL_SALES_ROUTE.name) {
            SeeAllSalesScreen(
                Modifier,
                viewModel,
                onBackButtonClicked = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}
