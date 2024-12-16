package br.com.lucramaisagenciadigital.registrapedidos.database

data class ClientData(
    val name: String,
    val requestNumber: Double,
    val saleItemList: List<SaleItem>
)
