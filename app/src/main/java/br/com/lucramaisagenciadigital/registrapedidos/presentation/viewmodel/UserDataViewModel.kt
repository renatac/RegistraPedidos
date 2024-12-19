package br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucramaisagenciadigital.registrapedidos.domain.Repository
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDataViewModel(val repository: Repository) : ViewModel() {

    private val _allUsersDataStateFlow = MutableStateFlow<List<UserData>?>(emptyList())
    val allUsersDataStateFlow: StateFlow<List<UserData>?> = _allUsersDataStateFlow

    private val _userDataStateFlow = MutableStateFlow<UserData?>(null)
    val userDataStateFlow: StateFlow<UserData?> = _userDataStateFlow

    val allUsersDataOrderByRequestNumber: Flow<List<UserData?>> = repository.usersDataOrderByRequestNumber

    fun insertUserData(userData: UserData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertUserData(userData)
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

    // fun deleteSaleItemByItemNumber(itemNumber: Long) {
    //     viewModelScope.launch {
    //         repository.deleteSaleItemByItemNumber(itemNumber)
    //     }
    //  }
}