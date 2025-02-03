package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import br.com.lucramaisagenciadigital.registrapedidos.newSaleItem
import org.junit.Assert.assertEquals
import org.junit.Test

class SaleItemTest {

    @Test
    fun `test SaleItem creation and properties`() {
        assertEquals(1L, newSaleItem.itemNumber)
        assertEquals(1L, newSaleItem.userDataId)
        assertEquals("Product 1", newSaleItem.product)
        assertEquals(10, newSaleItem.quantity)
        assertEquals(2.0, newSaleItem.unitaryValue, 0.001)
        assertEquals(20.0, newSaleItem.totalValue, 0.001)
    }
}