package screens.activities

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.activities_page
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.activity.Activity
import screens.home.Home

class Activities @OptIn(ExperimentalResourceApi::class) constructor(
    activitiesId: String,
    icon: DrawableResource
) : Screen {
    private val viewModel = ActivitiesViewModel()
    private val activities = activitiesId

    @OptIn(ExperimentalResourceApi::class)
    private val badgeIcon = icon

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CustomAppBar(
                    stringResource(Res.string.activities_page),
                    navigator,
                    Home()
                )
            },
            content = {
                ActivitiesScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onActivityClick = { activity -> navigator.push(Activity(activity.id, activities, badgeIcon)) },
                    onLikeClick = { state, id -> viewModel.onLikeClick(state, id) },
                    fetchData = { viewModel.fetchData(activities) },
                    type = activities,
                    icon = badgeIcon
                )
            }
        )
    }
}
