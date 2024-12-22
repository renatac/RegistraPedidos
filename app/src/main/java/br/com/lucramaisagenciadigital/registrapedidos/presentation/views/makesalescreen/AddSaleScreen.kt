package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.presentation.utils.components.RegisterOrdersTopAppBar
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.Buttons
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.SaleInput
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.ViewSales
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddSaleScreen(
    modifier: Modifier,
    viewModel: UserDataViewModel,
    navigateToMainScreen: () -> Unit
) {

    val allUsersData: List<UserData>? = viewModel.allUsersDataStateFlow.collectAsState().value
    val userData: UserData? = viewModel.userDataStateFlow.collectAsState().value

    val allUsersDataOrderByRequestNumber: List<UserData?> by viewModel.allUsersDataOrderByRequestNumber.collectAsState(
        initial = listOf<UserData>()
    )
    println(allUsersDataOrderByRequestNumber)

    // saleItemMutableStateList.addAll(userData?.saleItemList.orEmpty())
    AddSaleScreenContent(modifier, viewModel, navigateToMainScreen)
}

@Composable
fun AddSaleScreenContent(
    modifier: Modifier,
    viewModel: UserDataViewModel,
    navigateToMainScreen: () -> Unit
) {
    val saleItemMutableStateList = remember { mutableStateListOf<SaleItem>() }
    var clientName = String()
    Scaffold(
        topBar = {
            RegisterOrdersTopAppBar(
                Modifier,
                title = stringResource(id = R.string.doing_sale),
                onBackButtonClicked = { navigateToMainScreen.invoke() }
            )
        },
        bottomBar = {
            if (saleItemMutableStateList.isNotEmpty()) {
                Buttons(
                    modifier,
                    onCancelButtonClicked = {
                        navigateToMainScreen()
                    },
                    onSaveButtonClicked = {
                        val userData = UserData(
                            name = clientName,
                            requestNumber = 1,
                            saleItemList = saleItemMutableStateList
                        )
                        viewModel.insertUserData(userData)
                        navigateToMainScreen()
                    }
                )
            }
        },
        content = { contentPadding ->
            Column(
                modifier
                    .padding(contentPadding)
                    .background(Color.Yellow)
            ) {
                // TODO (Changing 1 to requestNumber)
                SaleInput(modifier, "1") { saleItem: SaleItem, name: String ->
                    clientName = name
                    saleItemMutableStateList.add(saleItem)
                }
                ViewSales(
                    modifier,
                    saleItemsList = saleItemMutableStateList,
                    onDeleteButtonClicked = { saleItem: SaleItem ->
                        saleItemMutableStateList.remove(saleItem)
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun MakeSaleScreenPreview() {
    val viewModel: UserDataViewModel = koinViewModel()
    AddSaleScreen(modifier = Modifier, viewModel, navigateToMainScreen = {})
}