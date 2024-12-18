package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.ui.theme.DarkBlue

@Composable
fun ViewSales(
    modifier: Modifier = Modifier,
    saleItemsList: List<SaleItem>? = emptyList(),
    onDeleteButtonClicked: (SaleItem) -> Unit
) {
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
                        ConstraintLayout(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            val (productLabelRef, quantityLabelRef, unitaryValueLabelRef) = createRefs()
                            val (totalRef, deleteButton) = createRefs()

                            Text(
                                text = stringResource(id = R.string.product, saleItem.product),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = modifier
                                    .padding()
                                    .fillMaxWidth()
                                    .constrainAs(productLabelRef) {
                                        top.linkTo(parent.top, margin = 16.dp)
                                        linkTo(
                                            start = parent.start,
                                            end = parent.end,
                                            startMargin = 16.dp,
                                            endMargin = 16.dp,
                                            bias = 0F
                                        )
                                        width = Dimension.wrapContent
                                    }
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.quantity,
                                    saleItem.quantity.toString()
                                ),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .constrainAs(quantityLabelRef) {
                                        top.linkTo(productLabelRef.bottom)
                                        linkTo(
                                            start = parent.start,
                                            end = parent.end,
                                            startMargin = 16.dp,
                                            endMargin = 16.dp,
                                            bias = 0F
                                        )
                                        width = Dimension.wrapContent
                                    }
                            )

                            Text(
                                text = stringResource(
                                    id = R.string.unitary_value,
                                    saleItem.unitaryValue.toString()
                                ),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.constrainAs(unitaryValueLabelRef) {
                                    top.linkTo(quantityLabelRef.bottom)
                                    linkTo(
                                        start = parent.start,
                                        end = parent.end,
                                        startMargin = 16.dp,
                                        endMargin = 16.dp,
                                        bias = 0F
                                    )
                                    width = Dimension.wrapContent
                                }
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.total_value,
                                    saleItem.totalValue.toString()
                                ),
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.constrainAs(totalRef) {
                                    top.linkTo(unitaryValueLabelRef.bottom, margin = 16.dp)
                                    linkTo(
                                        start = parent.start,
                                        end = parent.end,
                                        startMargin = 16.dp,
                                        endMargin = 16.dp,
                                        bias = 1F
                                    )
                                    width = Dimension.wrapContent
                                }
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_delete_24),
                                contentDescription = stringResource(id = R.string.content_description_trash_image),
                                modifier = Modifier
                                    .constrainAs(deleteButton) {
                                        top.linkTo(quantityLabelRef.top)
                                        bottom.linkTo(quantityLabelRef.bottom)
                                        end.linkTo(parent.end, margin = 16.dp)
                                    }
                                    .clickable {
                                        onDeleteButtonClicked(saleItem)
                                    })
                            HorizontalDivider(thickness = 2.dp)
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
            product = "Notebook",
            quantity = 2,
            unitaryValue = 1000.50,
            totalValue = 2100.00
        ),
        SaleItem(
            product = "Notebook",
            quantity = 2,
            unitaryValue = 1000.50,
            totalValue = 2100.00
        )
    )
    ViewSales(saleItemsList = saleItems) {}
}
