package screens.admin

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import screens.admin.activeRequests.Active
import screens.admin.previousRequests.Previous
import screens.auth.login.Login

class Admin : Screen {
    private val viewModel = AdminViewModel()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = { CustomAppBar("Admin", navigator, Admin()) },
            content = {
                AdminScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onActiveClick = {
                        navigator.push(Active())
                    },
                    onPreviousClick = {
                        navigator.push(Previous())
                    },
                    onSignOutClick = {
                        navigator.push(Login())
                    }
                )
            }
        )
    }
}