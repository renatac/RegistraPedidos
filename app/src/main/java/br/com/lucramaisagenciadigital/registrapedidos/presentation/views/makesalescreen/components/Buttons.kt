package br.com.lucramaisagenciadigital.registrapedidos.presentation.views.makesalescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.lucramaisagenciadigital.registrapedidos.R

@Composable
fun Buttons(
    modifier: Modifier,
    onCancelButtonClicked: () -> Unit,
    onSaveButtonClicked: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(Color.Yellow)
    ) {
        Button(modifier = modifier
            .padding(start = 16.dp, end = 8.dp, bottom = 16.dp)
            .weight(1F),
            onClick = { onCancelButtonClicked.invoke() }) {
            Text(text = stringResource(id = R.string.cleaning))
        }

        Button(modifier = modifier
            .padding(start = 8.dp, end = 16.dp, bottom = 16.dp)
            .weight(1F),
            onClick = { onSaveButtonClicked.invoke() }) {
            Text(text = stringResource(id = R.string.adding))
        }
    }
}
