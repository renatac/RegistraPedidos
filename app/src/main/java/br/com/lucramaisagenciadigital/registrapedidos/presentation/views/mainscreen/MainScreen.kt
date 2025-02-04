package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import br.com.lucramaisagenciadigital.registrapedidos.R
import br.com.lucramaisagenciadigital.registrapedidos.presentation.utils.components.RegisterOrdersTopAppBar

@Composable
fun MainScreen(
    modifier: Modifier,
    navigateToAddSaleScreen: () -> Unit,
    navigateToSeeAllSalesScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            RegisterOrdersTopAppBar(
                Modifier,
                title = stringResource(id = R.string.registry_requests)
            )
        },
        content = { contentPadding ->
            MainScreenContent(
                modifier.padding(contentPadding),
                navigateToAddSaleScreen,
                navigateToSeeAllSalesScreen
            )
        }
    )
}

@Composable
fun MainScreenContent(
    modifier: Modifier,
    navigateToAddSaleScreen: () -> Unit,
    navigateToSeeAllSalesScreen: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Yellow)
    ) {
        Text(
            text = stringResource(id = R.string.registry_requests, 0.toString()),
            style = typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        )
        Image(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_app_registration_24),
            contentDescription = stringResource(id = R.string.content_description_app_registration_image),
        )
        Text(
            text = stringResource(id = R.string.slogan_phase_subtitle, 0.toString()),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            onClick = {
                navigateToAddSaleScreen.invoke()
            }
        ) {
            Text(
                text = stringResource(id = R.string.registering_sales),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            onClick = {
                navigateToSeeAllSalesScreen.invoke()
            }) {
            Text(
                text = stringResource(id = R.string.see_sales_made),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(modifier = Modifier, {}, {})
}