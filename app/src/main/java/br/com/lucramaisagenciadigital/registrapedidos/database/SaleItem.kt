package br.com.lucramaisagenciadigital.registrapedidos.database

data class SaleItem(
    val product: String,
    val quantity: Int,
    val unitaryValue: Double,
    val totalValue: Double
)
