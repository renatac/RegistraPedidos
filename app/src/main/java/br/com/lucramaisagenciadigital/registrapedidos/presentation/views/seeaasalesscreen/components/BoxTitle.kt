package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.seeaasalesscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.lucramaisagenciadigital.registrapedidos.ui.theme.DarkBlue

@Composable
fun BoxTitle(
    modifier: Modifier,
    name: String
) {
    Box(
        Modifier
            .background(DarkBlue)
            .fillMaxWidth()
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun BoxTitlePreview() {
    BoxTitle(modifier = Modifier, "Name")
}