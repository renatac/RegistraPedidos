package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.seeaasalesscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.presentation.utils.components.RegisterOrdersTopAppBar
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import br.com.lucramaisagenciadigital.registrapedidos.presentation.views.seeaasalesscreen.components.ViewAllSales
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeeAllSalesScreen(
    modifier: Modifier,
    viewModel: UserDataViewModel,
    onBackButtonClicked: () -> Unit
) {
    val allUsersDataOrderByRequestNumber: List<UserData?> by viewModel.allUsersDataOrderByRequestNumber.collectAsState(
        initial = emptyList()
    )

    val userDataMutableStateList = remember { mutableStateListOf<UserData>() }

    LaunchedEffect(key1 = allUsersDataOrderByRequestNumber) {
        userDataMutableStateList.clear()
        allUsersDataOrderByRequestNumber.filterNotNull().let { userDataMutableStateList.addAll(it) }
    }
    SeeAllSalesScreenContent(
        modifier,
        userDataMutableStateList
    ) { onBackButtonClicked.invoke() }
}

@Composable
fun SeeAllSalesScreenContent(
    modifier: Modifier,
    userDataMutableStateList: SnapshotStateList<UserData>,
    onBackButtonClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            RegisterOrdersTopAppBar(
                Modifier,
                stringResource(id = R.string.all_sales),
                onBackButtonClicked = { onBackButtonClicked.invoke() }
            )
        },
        content = { contentPadding ->
            Column(
                modifier
                    .padding(contentPadding)
                    .background(Color.LightGray)
            ) {
                ViewAllSales(Modifier,
                    userDataList = userDataMutableStateList,
                    onDeleteButtonClicked = { userDataItem: UserData ->
                        userDataMutableStateList.remove(userDataItem)
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun SeeAllSalesScreenPreview() {
    val viewModel: UserDataViewModel = koinViewModel()
    SeeAllSalesScreen(modifier = Modifier, viewModel) {}
}