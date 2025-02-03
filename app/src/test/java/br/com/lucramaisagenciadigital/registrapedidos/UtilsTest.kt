package br.com.lucramaisagenciadigital.registrapedidos

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData

val expectedSaleItems = listOf(
    SaleItem(
        itemNumber = 1,
        userDataId = 1,
        product = "Product 1",
        quantity = 10,
        unitaryValue = 2.0,
        totalValue = 10.0
    ),
    SaleItem(
        itemNumber = 2,
        userDataId = 1,
        product = "Product Delta",
        quantity = 5,
        unitaryValue = 3.0,
        totalValue = 20.0
    )
).toMutableStateList()

val newUserData = UserData(
    requestNumber = 1,
    name = "Test User"
)
val expectedId = 1L

val itemNumber = 1L

val expectedRequestNumber = 1L

val clientName = mutableStateOf("Client 1")
val clientNameNull = null
val productName = mutableStateOf("Product 1")
val quantityText = mutableStateOf("15")
val unitValueText = mutableStateOf("7.5")
val totalQuantity = mutableStateOf(112.5)

val newSaleItem = SaleItem(
    itemNumber = 1L,
    userDataId = 1L,
    product = "Product 1",
    quantity = 10,
    unitaryValue = 2.0,
    totalValue = 20.0
)

val expectedUsers = listOf(
    UserData(requestNumber = 1, name = "Ana"),
    UserData(requestNumber = 2, name = "Paulo")
)

