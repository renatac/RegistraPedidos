package br.com.lucramaisagenciadigital.registrapedidos.views.makesale

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.database.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.views.makesale.components.SaleInput
import br.com.lucramaisagenciadigital.registrapedidos.views.makesale.components.ViewSales

@Composable
fun MakeSaleScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    MakeSaleScreenContent(modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeSaleScreenContent(modifier: Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.doing_sale),
                        fontSize = 20.sp
                    )
                },
            )
        },
        content = { contentPadding ->
            Column(
                modifier
                    .padding(contentPadding)
                    .background(Color.Yellow)
            ) {
                // TODO (Changing 1 to requestNumber)
                SaleInput(modifier, "1") {}
                ViewSales(
                    // TODO ("Removing mock")
                    saleItems = listOf(
                        SaleItem(
                            product = "Notebook",
                            quantity = 2,
                            unitaryValue = 1000.50,
                            totalValue = 2100.00
                        ),
                        SaleItem(
                            product = "Notebook",
                            quantity = 2,
                            unitaryValue = 1000.50,
                            totalValue = 2100.00
                        )
                    ),
                    onDeleteButtonClicked = {}
                )
            }
        }
    )
}

@Preview
@Composable
fun MakeSaleScreenPreview() {
    val navController = rememberNavController()
    MakeSaleScreen(modifier = Modifier, navController)
}