package screens.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.home_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.activities.Activities
import screens.events.Events

class Home : Screen {
    private val viewModel = HomeViewModel()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = { CustomAppBar(stringResource(Res.string.home_page), navigator, Home()) },
            content = {
                HomeScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onActivityClick = { activityId, icon ->
                        navigator.push(
                            Activities(
                                activityId,
                                icon
                            )
                        )
                    },
                    onEventsClick = {
                        navigator.push(Events())
                    }
                )
            }
        )
    }
}