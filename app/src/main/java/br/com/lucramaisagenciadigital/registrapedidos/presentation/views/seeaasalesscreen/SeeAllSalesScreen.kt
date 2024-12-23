package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.seeaasalesscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.presentation.utils.components.RegisterOrdersTopAppBar
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.seeaasalesscreen.components.ViewAllSales
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeeAllSalesScreen(
    modifier: Modifier,
    viewModel: UserDataViewModel,
    onBackButtonClicked: () -> Unit
) {
    val allUsersDataOrderByRequestNumber: List<UserData?> by viewModel.allUsersDataOrderByRequestNumber.collectAsState(
        initial = emptyList()
    )
    val allSaleItemsOrderByUserDataId: List<SaleItem?> by viewModel.allSaleItemsOrderByUserDataId.collectAsState(
        initial = emptyList()
    )

    SeeAllSalesScreenContent(
        modifier,
        viewModel,
        allUsersDataOrderByRequestNumber,
        allSaleItemsOrderByUserDataId,
        onBackButtonClicked
    )
}

@Composable
fun SeeAllSalesScreenContent(
    modifier: Modifier,
    viewModel: UserDataViewModel,
    userDataList: List<UserData?>,
    saleItemsList: List<SaleItem?>,
    onBackButtonClicked: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            RegisterOrdersTopAppBar(
                Modifier,
                stringResource(id = R.string.all_sales),
                onBackButtonClicked = { onBackButtonClicked.invoke() }
            )
        },
        content = { contentPadding ->
            Column(
                modifier
                    .padding(contentPadding)
                    .background(Color.LightGray)
            ) {
                ViewAllSales(
                    Modifier,
                    userDataList = userDataList,
                    saleItemList = saleItemsList,
                    onDeleteOneSaleButtonClicked = { saleItem: SaleItem ->
                        if(saleItemsList.filter { it?.userDataId == saleItem.userDataId }.size == 1) {
                            viewModel.deleteUserDataByRequestNumber(saleItem.userDataId)
                        }
                        coroutineScope.launch {
                            viewModel.deleteSaleItemByItemNumber(saleItem.itemNumber)
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (userDataList.isNotEmpty()) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 24.dp, end = 24.dp, bottom = 48.dp),
                    onClick = {
                        viewModel.deleteAllUserData()
                    }) {
                    Text(
                        text = stringResource(id = R.string.deleting_all_sales),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun SeeAllSalesScreenPreview() {
    val viewModel: UserDataViewModel = koinViewModel()
    SeeAllSalesScreen(modifier = Modifier, viewModel) {}
}