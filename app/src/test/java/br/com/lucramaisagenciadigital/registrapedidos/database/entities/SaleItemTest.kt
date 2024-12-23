package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import org.junit.Assert.assertEquals
import org.junit.Test

class SaleItemTest {

    @Test
    fun `test SaleItem creation and properties`() {
        val saleItem = SaleItem(
            itemNumber = 1L,
            userDataId = 123L,
            product = "Product A",
            quantity = 2,
            unitaryValue = 10.0,
            totalValue = 20.0
        )

        assertEquals(1L, saleItem.itemNumber)
        assertEquals(123L, saleItem.userDataId)
        assertEquals("Product A", saleItem.product)
        assertEquals(2, saleItem.quantity)
        assertEquals(10.0, saleItem.unitaryValue, 0.001)
        assertEquals(20.0, saleItem.totalValue, 0.001)
    }

    @Test
    fun `test default values`() {
        val saleItem = SaleItem(
            userDataId = 123L,
            product = "Product B",
            quantity = 1,
            unitaryValue = 5.0,
            totalValue = 5.0
        )
        assertEquals(0L, saleItem.itemNumber)
        assertEquals(123L, saleItem.userDataId)
        assertEquals("Product B", saleItem.product)
        assertEquals(1, saleItem.quantity)
        assertEquals(5.0, saleItem.unitaryValue, 0.001)
        assertEquals(5.0, saleItem.totalValue, 0.001)
    }
}