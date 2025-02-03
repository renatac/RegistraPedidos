package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.Deferred
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
    AddSaleScreenContent(modifier, viewModel, navigateToMainScreen)
}

@Composable
fun AddSaleScreenContent(
    modifier: Modifier,
    viewModel: UserDataViewModel,
    navigateToMainScreen: () -> Unit
) {
    val saleItemMutableStateList = remember { mutableStateListOf<SaleItem>() }
    var clientName = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

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
                            try {
                                val userDataId =
                                    viewModel.insertUserData(UserData(name = clientName.value))

                                //Inserting SaleItems for the UserData only after the UserData is inserted
                                val saleItemDeferreds: List<Deferred<Long>> =
                                    saleItemMutableStateList.map { saleItem: SaleItem ->
                                        val updatedSaleItem = saleItem.copy(userDataId = userDataId)
                                        async { viewModel.insertSaleItem(updatedSaleItem) }
                                    }

                                saleItemDeferreds.awaitAll() // Wait for all SaleItem insertions to complete
                                snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.sales_added),
                                    duration = SnackbarDuration.Short
                                )
                                navigateToMainScreen()
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar(
                                    message = context.getString(
                                        R.string.error_adding_sales,
                                        e.message ?: R.string.unknown_error
                                    ),
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { contentPadding ->
            Column(
                modifier
                    .padding(contentPadding)
                    .background(Color.Yellow)
                    .fillMaxSize()
            ) {
                SaleInput(
                    modifier,
                    viewModel,
                    onAddButtonClicked = { product, quantity, unitaryValue, totalValue, name ->
                        clientName.value = name
                        val saleItem = SaleItem(
                            userDataId = viewModel.userDataId,
                            product = product,
                            quantity = quantity,
                            unitaryValue = unitaryValue,
                            totalValue = totalValue
                        )
                        saleItemMutableStateList.add(saleItem)
                    },
                    showSnackbar = { message: String ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Long
                            )
                        }
                    }
                )
                val discountEmptyMessage = stringResource(id = R.string.discount_please)
                val discountBiggerWrongMessage = stringResource(id = R.string.discount_bigger_than_total_value)
                ViewSales(
                    saleItemsList = saleItemMutableStateList,
                    clientNameMutableState = clientName,
                    onDeleteButtonClicked = { saleItem: SaleItem ->
                        saleItemMutableStateList.remove(saleItem)
                    },
                    onCalculateDiscount = { discount, totalValue, isAddingDiscount ->
                        coroutineScope.launch {
                            if (discount.isEmpty()) {
                                snackbarHostState.showSnackbar(
                                    message = discountEmptyMessage,
                                    duration = SnackbarDuration.Short
                                )
                            } else if(discount.toDouble() >= totalValue && isAddingDiscount) {
                                snackbarHostState.showSnackbar(
                                    message = discountBiggerWrongMessage,
                                    duration = SnackbarDuration.Short
                                )
                            }
                            else {
                                try {
                                    viewModel.adjustDiscount(
                                        discount.toDouble(),
                                        saleItemMutableStateList,
                                        isAddingDiscount
                                    )
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar(
                                        message = context.getString(
                                            R.string.error_calculating_discount,
                                            e.message ?: R.string.unknown_error
                                        ),
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }
                        }
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