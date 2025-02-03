package br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.domain.Repository
import br.com.lucramaisagenciadigital.registrapedidos.presentation.ZERO_LONG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class UserDataViewModel(val repository: Repository) : ViewModel() {

    val allUsersDataOrderByRequestNumber: Flow<List<UserData?>> =
        repository.usersDataOrderByRequestNumber

    val allSaleItemsOrderByUserDataId: Flow<List<SaleItem?>> =
        repository.saleItemsOrderByUserDataId

    var userDataId: Long = ZERO_LONG

    var saleDataId: Long = ZERO_LONG

    fun adjustDiscount(
        discountNumber: Double,
        saleItemMutableStateList: SnapshotStateList<SaleItem>,
        isAddingDiscount: Boolean
    ) {
        val total = saleItemMutableStateList.sumOf { it.totalValue }
        val discountList = saleItemMutableStateList.map { item ->
            val adjustment = if (isAddingDiscount) {
                -discountNumber
            } else {
                discountNumber
            }
            item.copy(totalValue = item.totalValue + (adjustment * (item.totalValue / total)))
        }
        saleItemMutableStateList.clear()
        saleItemMutableStateList.addAll(discountList)
    }

    suspend fun insertUserData(userData: UserData): Long {
        return suspendCancellableCoroutine { continuation ->
            viewModelScope.launch {
                val id = repository.insertUserData(userData)
                userDataId = id
                continuation.resume(id)
            }
        }
    }

    fun deleteUserDataByRequestNumber(requestNumber: Long) {
        viewModelScope.launch {
            repository.deleteUserDataByRequestNumber(requestNumber)
        }
    }

    fun deleteAllUserData() {
        viewModelScope.launch {
            repository.deleteAllUserData()
        }
    }

    suspend fun insertSaleItem(saleItem: SaleItem): Long {
        return suspendCancellableCoroutine { continuation ->
            viewModelScope.launch {
                val id = repository.insertSaleItem(saleItem)
                saleDataId = id
                continuation.resume(id)
            }
        }
    }

    suspend fun deleteSaleItemByItemNumber(itemNumber: Long) {
        viewModelScope.launch {
            repository.deleteSaleItemByItemNumber(itemNumber)
        }
    }

    fun clearFields(
        clientName: MutableState<String>? = null,
        productName: MutableState<String>,
        quantityText: MutableState<String>,
        unitValueText: MutableState<String>,
        totalQuantity: MutableState<Double>
    ) {
        clientName?.let {
            it.value = String()
        }
        productName.value = String()
        quantityText.value = String()
        unitValueText.value = String()
        totalQuantity.value = 0.0
    }
}