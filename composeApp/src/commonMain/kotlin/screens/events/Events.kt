package screens.events

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.events_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.event.SelectedEvent
import screens.home.Home

class Events : Screen {
    private val viewModel = EventsViewModel()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CustomAppBar(
                    stringResource(Res.string.events_page),
                    navigator,
                    Home()
                )
            },
            content = {
                EventsScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onEventClick = { event, status -> navigator.push(SelectedEvent(event, status)) },
                    onLikeClick = { state, id -> viewModel.onLikeClick(state, id) },
                    fetchData = { viewModel.fetchData() }
                )
            }
        )
    }
}