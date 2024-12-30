package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import br.com.lucramaisagenciadigital.registrapedidos.presentation.ZERO_DOUBLE
import br.com.lucramaisagenciadigital.registrapedidos.presentation.ZERO_INT

private const val UNITARY_VALUE_MAX_ALLOWED = 50000
private const val QUANTITY_MAX_ALLOWED = 100

@Composable
fun SaleInput(
    modifier: Modifier,
    onAddButtonClicked: (String, Int, Double, Double, String) -> Unit,
    showSnackbar: (String) -> Unit
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
        // It stores the quantity value show the text field
        var quantityText by remember { mutableStateOf(quantity.intValue.toString()) }
        val unitaryValue = remember { mutableDoubleStateOf(ZERO_DOUBLE) }
        // It stores the unitary value show the text field
        var unitaryValueText by remember { mutableStateOf(unitaryValue.doubleValue.toString()) }
        val totalQuantity = remember { mutableDoubleStateOf(ZERO_DOUBLE) }
        totalQuantity.doubleValue = quantity.intValue * unitaryValue.doubleValue

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        ) {
            Box(
                modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .background(Color.Blue)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.fill_sale_register),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .align(Alignment.Center)
                )
            }
            TextField(
                value = clientName.value,
                onValueChange = { clientName.value = it },
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.client_name)) },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = productName.value,
                onValueChange = { productName.value = it },
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.product_name)) },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier.fillMaxWidth()
            ) {
                TextField(
                    value = quantityText,
                    onValueChange = { newText ->
                        if (newText.isEmpty() || newText.isBlank()) {
                            quantity.intValue = ZERO_INT
                            quantityText = ""
                        } else {
                            val newQuantity = newText.toIntOrNull()
                            if (newQuantity != null) {
                                if (newQuantity <= QUANTITY_MAX_ALLOWED) {
                                    quantity.intValue = newQuantity
                                    quantityText = newText
                                } else {
                                    // If the new quantity exceeds the limit, only updates the text to the previous value
                                    quantityText = quantity.intValue.toString()
                                }
                            }
                        }
                    },
                    singleLine = true,
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
                            Text(text = stringResource(id = R.string.bigger_than_zero_and_smaller_than_one_hundred_message))
                        }
                    },
                    isError = quantity.intValue == ZERO_INT,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                        .fillMaxWidth()
                        .weight(1F)
                )
                TextField(
                    value = unitaryValueText,
                    onValueChange = { newText ->
                        if (newText.isEmpty() || newText.isBlank()) {
                            unitaryValue.doubleValue = ZERO_DOUBLE
                            unitaryValueText = ""
                        } else {
                            val newUnitaryValue = newText.toDoubleOrNull()
                            if (newUnitaryValue != null) {
                                if (newUnitaryValue <= UNITARY_VALUE_MAX_ALLOWED) {
                                    unitaryValue.doubleValue = newUnitaryValue
                                    unitaryValueText = newText
                                } else {
                                    // If the new unitaryValue exceeds the limit, only updates the text to the previous value
                                    unitaryValueText = unitaryValue.doubleValue.toString()
                                }
                            }
                        }
                    },
                    singleLine = true,
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
                            Text(text = stringResource(id = R.string.bigger_than_zero_and_smaller_than_fifty_thousand_message))
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
                        onAddButtonClicked(
                            productName.value,
                            quantity.intValue,
                            unitaryValue.doubleValue,
                            totalQuantity.doubleValue,
                            clientName.value
                        )
                        clearFields(
                            productName,
                            quantity,
                            unitaryValue,
                            totalQuantity
                        )
                    } else {
                        showSnackbar.invoke(emptyFieldsString)
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
    quantity.intValue = ZERO_INT
    unitValue.doubleValue = ZERO_DOUBLE
    totalQuantity.doubleValue = ZERO_DOUBLE
}

@Preview
@Composable
fun SaleInputPreview() {
    SaleInput(modifier = Modifier, { _, _, _, _, _ -> }, { _ -> })
}
