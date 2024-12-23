package br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserDataWithSaleItems
import br.com.lucramaisagenciadigital.registrapedidos.domain.Repository
import br.com.lucramaisagenciadigital.registrapedidos.presentation.ZERO_LONG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class UserDataViewModel(val repository: Repository) : ViewModel() {

    private val _allUsersDataStateFlow = MutableStateFlow<List<UserData>?>(emptyList())
    val allUsersDataStateFlow: StateFlow<List<UserData>?> = _allUsersDataStateFlow

    private val _userDataStateFlow = MutableStateFlow<UserData?>(null)
    val userDataStateFlow: StateFlow<UserData?> = _userDataStateFlow

    private val _userDataWithSaleItems = MutableStateFlow<UserDataWithSaleItems?>(null)
    val userDataWithSaleItems: StateFlow<UserDataWithSaleItems?> = _userDataWithSaleItems

    val allUsersDataOrderByRequestNumber: Flow<List<UserData?>> =
        repository.usersDataOrderByRequestNumber

    val allSaleItemsOrderByUserDataId: Flow<List<SaleItem?>> =
        repository.saleItemsOrderByUserDataId

    var userDataId: Long = ZERO_LONG
    var saleDataId: Long = ZERO_LONG

    suspend fun insertUserData(userData: UserData): Long {
        return suspendCancellableCoroutine { continuation ->
            viewModelScope.launch {
                val id = repository.insertUserData(userData)
                userDataId = id
                continuation.resume(id)
            }
        }
    }

    // TODO("Esse app será continuado e usarei todas essas funções abaixo,
    // pois terei uma tela de filtros para a melhor visualização.")
    fun getUserDataByRequestNumber(requestNumber: Long) {
        viewModelScope.launch {
            _userDataStateFlow.value = repository.getUserDataByRequestNumber(requestNumber)
        }
    }

    fun getUserDataByUserName(name: String) {
        viewModelScope.launch {
            _userDataStateFlow.value = repository.getUserDataByUserName(name)
        }
    }

    suspend fun getUserDataWithSaleItems(requestNumber: Long) {
        _userDataWithSaleItems.value = viewModelScope.async {
            withContext(Dispatchers.IO) {
                repository.getUserDataWithSaleItems(requestNumber)
            }
        }.await()
    }

    fun deleteUserDataByRequestNumber(requestNumber: Long) {
        viewModelScope.launch {
            repository.deleteUserDataByRequestNumber(requestNumber)
        }
    }

    fun deleteUserDataByName(name: String) {
        viewModelScope.launch {
            repository.deleteUserDataByName(name)
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

    suspend fun deleteSaleItem(saleItem: SaleItem) {
        viewModelScope.launch {
            repository.deleteSaleItem(saleItem)
        }
    }
}