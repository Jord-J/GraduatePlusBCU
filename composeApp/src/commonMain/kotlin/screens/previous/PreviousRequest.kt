package screens.previous

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import common.components.Previous
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.activity_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

class PreviousRequest(
    previous: Previous
) : Screen {
    private val viewModel = PreviousRequestViewModel()
    private val previousItem = previous

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CustomAppBar(
                    stringResource(Res.string.activity_page),
                    navigator,
                    screens.admin.previousRequests.Previous()
                )
            },
            content = {
                PreviousRequestScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    fetchData = { viewModel.fetchData(previousItem) }
                )
            }
        )
    }
}