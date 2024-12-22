package br.com.lucramaisagenciadigital.registrapedidos.presentation.utils.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterOrdersTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color = Color.Black,
    onBackButtonClicked: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        ),
        title = {
            Text(
                text = title,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            onBackButtonClicked ?: return@TopAppBar
            IconButton(
                onClick = {
                    onBackButtonClicked.invoke()
                }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}