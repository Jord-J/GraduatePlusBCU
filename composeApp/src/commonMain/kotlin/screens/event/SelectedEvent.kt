package screens.event

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import common.components.EventData
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.event_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.auth.login.Login
import screens.events.Events

class SelectedEvent(
    event: EventData,
    status: String
) : Screen {
    private val viewModel = SelectedEventViewModel()
    private val eventItem = event
    private val currentStatus = status

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CustomAppBar(
                    stringResource(Res.string.event_page),
                    navigator,
                    Events()
                )
            },
            content = {
                SelectedEventScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    fetchData = { viewModel.fetchData(eventItem, currentStatus) },
                    goToLogin = {
                        navigator.push(Login())
                    },
                    updateEnrolled = { viewModel.updateEnrolled() }
                )
            }
        )
    }
}