package br.com.lucramaisagenciadigital.registrapedidos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.lucramaisagenciadigital.registrapedidos.navigation.NavHost
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import br.com.lucramaisagenciadigital.registrapedidos.ui.theme.RegisterRequestsTheme
import org.koin.androidx.compose.koinViewModel

const val ZERO_DOUBLE = 0.0
const val ZERO_INT = 0
const val ZERO_LONG = 0L

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHost = rememberNavController()
            val viewModel: UserDataViewModel = koinViewModel()

            RegisterRequestsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        viewModel,
                        navHostController = navHost
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    val navHost = rememberNavController()
    val viewModel: UserDataViewModel = koinViewModel()
    RegisterRequestsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray
        ) {
            NavHost(
                viewModel,
                navHostController = navHost
            )
        }
    }
}