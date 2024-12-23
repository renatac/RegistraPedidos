package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
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
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
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
                        coroutineScope.launch {
                            val userDataId = viewModel.insertUserData(UserData(name = clientName))

                            //Inserting SaleItems for the UserData only after the UserData is inserted
                            val saleItemDeferreds = saleItemMutableStateList.map { saleItem ->
                                val updatedSaleItem = saleItem.copy(userDataId = userDataId)
                                async { viewModel.insertSaleItem(updatedSaleItem) }
                            }

                            saleItemDeferreds.awaitAll() // Wait for all SaleItem insertions to complete
                        }
                        Toast.makeText(context, context.getString(R.string.sales_added), Toast.LENGTH_SHORT).show()
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
                SaleInput(modifier) { product, quantity, unitaryValue, totalValue, name ->
                    clientName = name
                    val saleItem = SaleItem(
                        userDataId = viewModel.userDataId,
                        product = product,
                        quantity = quantity,
                        unitaryValue = unitaryValue,
                        totalValue = totalValue
                    )
                    saleItemMutableStateList.add(saleItem)
                }
                ViewSales(
                    modifier,
                    saleItemsList = saleItemMutableStateList,
                    onDeleteButtonClicked = { saleItem: SaleItem ->
                        saleItemMutableStateList.remove(saleItem)
                    },
                    onCalculateDiscount = { discount ->
                        calculateDiscount(discount, saleItemMutableStateList)
                    }
                )
            }
        }
    )
}

fun calculateDiscount(
    discountNumber: Double,
    saleItemMutableStateList: SnapshotStateList<SaleItem>
) {
    val total = saleItemMutableStateList.sumOf { it.totalValue }
    val saleList = mutableListOf<SaleItem>()

    saleItemMutableStateList.forEach {
        saleList.add(it.copy(totalValue = it.totalValue -
                (discountNumber * (it.totalValue / total))))
    }
    saleItemMutableStateList.clear()
    saleItemMutableStateList.addAll(saleList)
}

@Preview
@Composable
fun MakeSaleScreenPreview() {
    val viewModel: UserDataViewModel = koinViewModel()
    AddSaleScreen(modifier = Modifier, viewModel, navigateToMainScreen = {})
}