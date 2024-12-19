package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.seeaasalesscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    userDataList: List<UserData>? = emptyList(),
    onDeleteButtonClicked: (UserData) -> Unit
) {
    Column(Modifier.background(Color.LightGray)) {
        if (userDataList?.isNotEmpty() == true) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize()
            ) {
                items(userDataList) { userData ->
                    Column(modifier = Modifier.padding(16.dp)) {
                        BoxTitle(Modifier, stringResource(id = R.string.client, userData.name))
                        userData.saleItemList.forEachIndexed { index, saleItem ->
                            ViewSaleItem(
                                modifier,
                                saleItem
                            ) {
                                // TODO ( Manipular onDeleteButtonClicked, provavelmente usando o
                                // index --> Type mismatch.
                                //Required: UserData
                                //Found: SaleItem
                                //onDeleteButtonClicked(it)
                            }
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.LightGray)
            ) {
                Image(
                    modifier = modifier
                        .size(100.dp)
                        .padding(top = 24.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.ic_app_registration_24),
                    contentDescription = stringResource(id = R.string.content_description_app_registration_image),
                )
                Text(
                    text = stringResource(id = R.string.you_do_not_have_sales),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun ViewAllSalesPreview() {
    val userDataList = listOf(
        UserData(0, "User A", listOf(SaleItem(0, "Product X", 5, 2.5, 12.5))),
        UserData(
            1,
            "User B",
            listOf(SaleItem(1, "Product Y", 2, 10.0, 20.0), SaleItem(4, "Product Z", 3, 5.0, 15.0))
        ),
        UserData(
            2,
            "User B",
            listOf(SaleItem(2, "Product Y", 2, 10.0, 20.0), SaleItem(5, "Product Z", 3, 5.0, 15.0))
        ),
        UserData(
            3,
            "User B",
            listOf(SaleItem(3, "Product Y", 2, 10.0, 20.0), SaleItem(6, "Product Z", 3, 5.0, 15.0))
        )
    )
    ViewAllSales(userDataList = userDataList) {}
}
