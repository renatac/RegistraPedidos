package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.SaleInput
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.ViewSales
import org.koin.androidx.compose.koinViewModel

@Composable
fun MakeSaleScreen(
    modifier: Modifier,
    sharedViewModel: UserDataViewModel,
    navController: NavHostController
) {

    val allUsersData: List<UserData>? = sharedViewModel.allUsersDataStateFlow.collectAsState().value
    val userData: UserData? = sharedViewModel.userDataStateFlow.collectAsState().value

    MakeSaleScreenContent(modifier, userData?.saleItemList?.toMutableStateList())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeSaleScreenContent(modifier: Modifier, saleItemList: MutableList<SaleItem>?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.doing_sale),
                        fontSize = 20.sp
                    )
                },
            )
        },
        content = { contentPadding ->
            Column(
                modifier
                    .padding(contentPadding)
                    .background(Color.Yellow)
            ) {
                // TODO (Changing 1 to requestNumber)
                SaleInput(modifier, "1") { saleItem: SaleItem ->
                    saleItemList?.add(saleItem)
                }
                ViewSales(
                    modifier,
                    saleItemsList = saleItemList,
                    onDeleteButtonClicked = { saleItem: SaleItem ->
                        saleItemList?.remove(saleItem)
                    }
                )
            }
        })
}

@Preview
@Composable
fun MakeSaleScreenPreview() {
    val viewModel: UserDataViewModel = koinViewModel()

    val navController = rememberNavController()
    MakeSaleScreen(modifier = Modifier, viewModel, navController)
}