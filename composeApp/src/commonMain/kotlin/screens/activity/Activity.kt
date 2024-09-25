package screens.activity

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.activity_page
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.activities.Activities

class Activity @OptIn(ExperimentalResourceApi::class) constructor(
    activity: String,
    activities: String,
    icon: DrawableResource
) : Screen {
    private val viewModel = ActivityViewModel()
    private val activityId = activity
    private val activitiesId = activities

    @OptIn(ExperimentalResourceApi::class)
    private val badgeIcon = icon

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CustomAppBar(
                    stringResource(Res.string.activity_page),
                    navigator,
                    Activities(activitiesId, badgeIcon)
                )
            },
            content = {
                ActivityScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    fetchData = { viewModel.fetchData(activityId) },
                    onActivityTextFieldChange = viewModel::updateActivityText,
                    onSubmitClick = { viewModel.onSubmit() },
                    goToPreviousPage = { navigator.push(Activities(activitiesId, badgeIcon)) }
                )
            }
        )
    }
}