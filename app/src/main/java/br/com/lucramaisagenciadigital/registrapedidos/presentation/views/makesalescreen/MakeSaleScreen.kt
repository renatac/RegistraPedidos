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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.Buttons
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.SaleInput
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.ViewSales
import org.koin.androidx.compose.koinViewModel

@Composable
fun MakeSaleScreen(
    modifier: Modifier,
    viewModel: UserDataViewModel,
    navigateToMainScreen: () -> Unit
) {

    val allUsersData: List<UserData>? = viewModel.allUsersDataStateFlow.collectAsState().value
    val userData: UserData? = viewModel.userDataStateFlow.collectAsState().value

    val saleItemMutableStateList = remember { mutableStateListOf<SaleItem>() }
    saleItemMutableStateList.addAll(userData?.saleItemList.orEmpty())
    MakeSaleScreenContent(modifier, viewModel, saleItemMutableStateList, navigateToMainScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeSaleScreenContent(
    modifier: Modifier,
    viewModel: UserDataViewModel,
    saleItemList: SnapshotStateList<SaleItem>,
    navigateToMainScreen: () -> Unit
) {
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
                var clientName = String()
                // TODO (Changing 1 to requestNumber)
                SaleInput(modifier, "1") { saleItem: SaleItem, name: String ->
                    clientName = name
                    saleItemList.add(saleItem)
                }
                ViewSales(
                    modifier,
                    saleItemsList = saleItemList,
                    onDeleteButtonClicked = { saleItem: SaleItem ->
                        saleItemList.remove(saleItem)
                    }
                )
                Buttons(
                    modifier,
                    onCancelButtonClicked = {
                        navigateToMainScreen()
                    },
                    onSaveButtonClicked = {
                        val userData = UserData(
                            name = clientName,
                            requestNumber = 1,
                            saleItemList = saleItemList
                        )
                        viewModel.insertUserData(userData)
                        navigateToMainScreen()
                    }
                )
            }
        })
}

@Preview
@Composable
fun MakeSaleScreenPreview() {
    val viewModel: UserDataViewModel = koinViewModel()
    MakeSaleScreen(modifier = Modifier, viewModel, navigateToMainScreen = {})
}