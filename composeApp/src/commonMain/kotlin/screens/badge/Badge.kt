package screens.badge

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.badge_page
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.account.profile.Account

class Badge @OptIn(ExperimentalResourceApi::class) constructor(
    award: String,
    icon: DrawableResource
) : Screen {
    private val viewModel = BadgeViewModel()

    private val awardType = award

    @OptIn(ExperimentalResourceApi::class)
    private val badgeIcon = icon

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow


        Scaffold(
            topBar = { CustomAppBar(stringResource(Res.string.badge_page), navigator, Account()) },
            content = {
                BadgeScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    icon = badgeIcon,
                    fetchData = { viewModel.fetchData(awardType) },
                    type = awardType
                )
            }
        )
    }
}