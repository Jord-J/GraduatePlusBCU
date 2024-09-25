package screens.admin.previousRequests

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import screens.admin.Admin
import screens.previous.PreviousRequest

class Previous : Screen {
    private val viewModel = PreviousViewModel()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = { CustomAppBar("Previous Requests", navigator, Admin()) },
            content = {
                PreviousScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onPreviousClick = { previous -> navigator.push(PreviousRequest(previous)) }
                )
            }
        )
    }
}