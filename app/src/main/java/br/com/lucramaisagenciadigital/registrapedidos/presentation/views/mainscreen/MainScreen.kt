package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.navigation.ScreenRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.registry_requests),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.background(Color.Black))
        },
        content = { contentPadding ->
            MainScreenContent(
                modifier.padding(contentPadding),
                navController
            )
        }
    )
}

@Composable
fun MainScreenContent(
    modifier: Modifier,
    navController: NavHostController
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.Yellow)) {
        Image(
            modifier = modifier
                .size(200.dp)
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_app_registration_24),
            contentDescription = stringResource(id = R.string.content_description_app_registration_image),
        )
        // TODO("Trocar o zero pelo valor total de verdade")
        Text(
            text = stringResource(id = R.string.sales_total, 0.toString()),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            onClick = {
                navController.navigate(ScreenRoutes.MAKING_SALE.name)
            }) {
            Text(
                text = stringResource(id = R.string.doing_sale),
                fontSize = 22.sp
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            onClick = {
                // TODO
            }) {
            Text(
                text = stringResource(id = R.string.see_sales_made),
                fontSize = 22.sp
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(modifier = Modifier, navController)
}