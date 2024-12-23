package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import br.com.lucramaisagenciadigital.registrapedidos.ui.theme.DarkBlue

@Composable
fun ViewSales(
    modifier: Modifier = Modifier,
    saleItemsList: List<SaleItem>? = emptyList(),
    onDeleteButtonClicked: (SaleItem) -> Unit,
    onCalculateDiscount: (Double) -> Unit
) {
    val discountNumber = remember { mutableStateOf(String()) }

    Column(Modifier.background(Color.Yellow)) {
        Box(
            Modifier
                .background(DarkBlue)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.sales_added),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
        if (saleItemsList?.isNotEmpty() == true) {
            Card(
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = saleItemsList
                    ) { saleItem ->
                        ViewSaleItem(
                            modifier,
                            saleItem
                        ) {
                            onDeleteButtonClicked(it)
                        }
                    }
                    item {
                        TextField(
                            value = discountNumber.value,
                            onValueChange = { discountNumber.value = it },
                            singleLine = true,
                            placeholder = { Text(text = stringResource(id = R.string.discount)) },
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                                .fillMaxWidth()
                        )
                        Button(modifier = modifier
                            .padding(start = 16.dp, end = 8.dp, bottom = 16.dp),
                            onClick = {
                                onCalculateDiscount(discountNumber.value.toDouble())
                            }) {
                            Text(text = stringResource(id = R.string.apply_discount))
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
                    text = stringResource(id = R.string.empty_list_message, 0.toString()),
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
fun ViewSalesPreview() {
    // TODO ("Removing mock")
    val saleItems = listOf(
        SaleItem(
            1,
            1,
            product = "Notebook",
            quantity = 2,
            unitaryValue = 1000.50,
            totalValue = 2100.00
        ),
        SaleItem(
            1,
            1,
            product = "Notebook",
            quantity = 2,
            unitaryValue = 1000.50,
            totalValue = 2100.00
        )
    )
    ViewSales(saleItemsList = saleItems, onDeleteButtonClicked= {_ ->}, onCalculateDiscount = {_ ->})
}
