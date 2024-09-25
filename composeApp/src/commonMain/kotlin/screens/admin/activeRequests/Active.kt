package screens.admin.activeRequests

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import screens.active.ActiveRequest
import screens.admin.Admin

class Active : Screen {
    private val viewModel = ActiveViewModel()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = { CustomAppBar("Active Requests", navigator, Admin()) },
            content = {
                ActiveScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onActiveClick = { active -> navigator.push(ActiveRequest(active)) }
                )
            }
        )
    }
}