package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem

@Composable
fun ViewSaleItem(
    modifier: Modifier,
    saleItem: SaleItem,
    onDeleteButtonClicked: (SaleItem) -> Unit
) {
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
        HorizontalDivider(thickness = 4.dp)
    }
}

@Preview
@Composable
fun ViewSaleItemScreenPreview() {
    ViewSaleItem(
        modifier = Modifier, saleItem =
        SaleItem(1, "Product Y", 2, 10.0, 20.0)
    ) { _ -> }
}