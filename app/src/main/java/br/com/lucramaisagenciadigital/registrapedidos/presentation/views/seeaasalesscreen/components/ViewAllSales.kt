package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.seeaasalesscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components.ViewSaleItem

@Composable
fun ViewAllSales(
    modifier: Modifier = Modifier,
    userDataList: List<UserData?> = emptyList(),
    saleItemList: List<SaleItem?> = emptyList(),
    onDeleteOneSaleButtonClicked: (Long) -> Unit
) {
    Column(Modifier.background(Color.LightGray)) {
        if (userDataList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize()
            ) {
                items(userDataList) { userData: UserData? ->
                    val saleList = saleItemList.filter { it?.userDataId == userData?.requestNumber }
                    Column(modifier = Modifier.padding(16.dp)) {
                        BoxTitle(
                            Modifier,
                            stringResource(id = R.string.client, userData?.name.orEmpty())
                        )
                        saleList.forEach { saleItem ->
                            saleItem?.let {
                                ViewSaleItem(
                                    modifier,
                                    it
                                ) { item ->
                                    onDeleteOneSaleButtonClicked(item.itemNumber)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    text = stringResource(id = R.string.you_do_not_have_sales),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Image(
                    modifier = modifier.size(400.dp),
                    painter = painterResource(id = R.drawable.ic_app_registration_24),
                    contentDescription = stringResource(id = R.string.content_description_app_registration_image),
                )
            }
        }
    }
}

@Preview
@Composable
fun ViewAllSalesPreview() {
    val userDataList = listOf(
        UserData(0, "User A"),
        UserData(
            1L,
            "User B"
        ),
        UserData(
            2L,
            "User B"
        ),
        UserData(
            3L,
            "User B"
        )
    )
    ViewAllSales(userDataList = userDataList) {}
}
