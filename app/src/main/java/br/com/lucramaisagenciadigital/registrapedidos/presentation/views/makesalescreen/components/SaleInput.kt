package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.presentation.ZERO_DOUBLE
import br.com.lucramaisagenciadigital.registrapedidos.presentation.ZERO_INT

private const val UNITARY_VALUE_MAX_ALLOWED = 50000
private const val QUANTITY_MAX_ALLOWED = 100

@Composable
fun SaleInput(
    modifier: Modifier,
    requestNumber: String,
    onAddButtonClicked: (SaleItem, String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        )
    ) {
        val clientName = remember { mutableStateOf(String()) }
        val productName = remember { mutableStateOf(String()) }
        val quantity: MutableIntState = remember { mutableIntStateOf(ZERO_INT) }
        val unitaryValue = remember { mutableDoubleStateOf(ZERO_DOUBLE) }
        val totalQuantity = remember { mutableDoubleStateOf(ZERO_DOUBLE) }
        totalQuantity.doubleValue = quantity.intValue * unitaryValue.doubleValue

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.fill_sale_register, requestNumber),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 20.dp)
                        .weight(1F)
                )
                Box(
                    modifier
                        .padding(end = 16.dp, top = 20.dp)
                        .background(Color.Blue)
                        .weight(0.5F)
                ) {
                    Text(
                        text = stringResource(id = R.string.request_number),
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }
            }
            TextField(
                value = clientName.value,
                onValueChange = { clientName.value = it },
                placeholder = { Text(text = stringResource(id = R.string.client_name)) },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = productName.value,
                onValueChange = { productName.value = it },
                placeholder = { Text(text = stringResource(id = R.string.product_name)) },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier.fillMaxWidth()
            ) {
                TextField(
                    value = if (quantity.intValue == ZERO_INT) {
                        String()
                    } else {
                        quantity.intValue.toString()
                    },
                    singleLine = true,
                    onValueChange = {
                        if (quantity.intValue <= QUANTITY_MAX_ALLOWED) {
                            if (it.isEmpty() || it.isBlank()) {
                                quantity.intValue = ZERO_INT
                            } else {
                                quantity.intValue = it.toInt()
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.quantity_label)
                        )
                    },
                    supportingText = {
                        if (quantity.intValue == ZERO_INT) {
                            Text(text = stringResource(id = R.string.bigger_than_zero_int_message))
                        } else if (quantity.intValue > QUANTITY_MAX_ALLOWED) {
                            Text(text = stringResource(id = R.string.smaller_than_one_hundred_message))
                        }
                    },
                    isError = quantity.intValue == ZERO_INT,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                        .fillMaxWidth()
                        .weight(1F)
                )
                TextField(
                    value = if (unitaryValue.doubleValue == ZERO_DOUBLE) {
                        String()
                    } else {
                        unitaryValue.doubleValue.toString()
                    },
                    singleLine = true,
                    onValueChange = {
                        if (unitaryValue.doubleValue <= UNITARY_VALUE_MAX_ALLOWED) {
                            if (it.isEmpty() || it.isBlank()) {
                                unitaryValue.doubleValue = ZERO_DOUBLE
                            } else {
                                unitaryValue.doubleValue = it.toDouble()
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(
                                id = R.string.unitary_value_label
                            )
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    supportingText = {
                        if (unitaryValue.doubleValue == ZERO_DOUBLE) {
                            Text(text = stringResource(id = R.string.bigger_than_zero_double_message))
                        } else if (unitaryValue.doubleValue > UNITARY_VALUE_MAX_ALLOWED) {
                            Text(text = stringResource(id = R.string.smaller_than_fifty_thousand_message))
                        }
                    },
                    isError = unitaryValue.doubleValue == ZERO_DOUBLE,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                        .fillMaxWidth()
                        .weight(1F)
                )
            }
            Text(
                text = stringResource(
                    id = R.string.total_value,
                    totalQuantity.doubleValue.toString()
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End)
            )
        }

        Row(
            modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        ) {
            Button(modifier = modifier
                .padding(start = 16.dp, end = 8.dp, bottom = 16.dp)
                .weight(1F),
                onClick = {
                    clearFields(
                        productName,
                        quantity,
                        unitaryValue,
                        totalQuantity
                    )
                }) {
                Text(text = stringResource(id = R.string.cleaning))
            }
            val emptyFieldsString = stringResource(R.string.empty_fields)
            val context = LocalContext.current
            Button(modifier = modifier
                .padding(start = 8.dp, end = 16.dp, bottom = 16.dp)
                .weight(1F),
                onClick = {
                    if (isFieldsNotEmpties(
                            clientName.value,
                            productName.value,
                            quantity.intValue,
                            unitaryValue.doubleValue,
                            totalQuantity.doubleValue
                        )
                    ) {
                        val saleItem = SaleItem(
                            product = productName.value,
                            quantity = quantity.intValue,
                            unitaryValue = unitaryValue.doubleValue,
                            totalValue = totalQuantity.doubleValue
                        )
                        clearFields(
                            productName,
                            quantity,
                            unitaryValue,
                            totalQuantity
                        )
                        onAddButtonClicked(saleItem, clientName.value)
                    } else {
                        Toast.makeText(context, emptyFieldsString, Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.adding))
            }
        }
    }
}

private fun isFieldsNotEmpties(
    name: String,
    product: String,
    quantity: Int,
    unitaryValue: Double,
    totalValue: Double
): Boolean {
    return (name.isNotEmpty() && name.isNotBlank()) &&
            (product.isNotEmpty() && product.isNotBlank()) &&
            quantity != ZERO_INT &&
            unitaryValue != ZERO_DOUBLE &&
            totalValue != ZERO_DOUBLE
}

private fun clearFields(
    productName: MutableState<String>,
    quantity: MutableIntState,
    unitValue: MutableDoubleState,
    totalQuantity: MutableDoubleState
) {
    productName.value = String()
    quantity.intValue = 0
    unitValue.doubleValue = 0.0
    totalQuantity.doubleValue = 0.0
}

@Preview
@Composable
fun SaleInputPreview() {
    SaleInput(modifier = Modifier, requestNumber = "1") { _, _ -> }
}