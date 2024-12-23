package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usersData")
data class UserData(
    @PrimaryKey(autoGenerate = true) val requestNumber: Long = 0L,
    val name: String
)
