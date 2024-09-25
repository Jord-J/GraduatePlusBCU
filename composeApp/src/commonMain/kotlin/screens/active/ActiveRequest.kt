package screens.active

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.Active
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.activity_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.admin.Admin
import screens.auth.login.Login

class ActiveRequest(
    active: Active
) : Screen {
    private val viewModel = ActiveRequestViewModel()
    private val activeItem = active

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { CustomAppBar(stringResource(Res.string.activity_page), navigator, Admin()) },
            content = {
                ActiveRequestScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    fetchData = { viewModel.fetchData(activeItem) },
                    onButtonClick = { bool -> viewModel.onButtonClick(bool) },
                    goToPrevious = {
                        navigator.push(screens.admin.activeRequests.Active())
                    },
                    goToLogin = {
                        navigator.push(Login())
                    }
                )
            }
        )
    }
}