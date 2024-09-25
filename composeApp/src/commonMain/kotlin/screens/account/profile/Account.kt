package screens.account.profile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.account_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.account.edit.Edit
import screens.auth.login.Login
import screens.badge.Badge
import screens.home.Home

class Account : Screen {
    private val viewModel = AccountViewModel()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { CustomAppBar(stringResource(Res.string.account_page), navigator, Home()) },
            content = {
                AccountScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    fetchData = { viewModel.fetchData() },
                    onButtonClick = { navigator.push(Edit()) },
                    onSignOut = { viewModel.signOut() },
                    signOut = {
                        navigator.push(Login())
                    },
                    goToAward = { award, icon -> navigator.push(Badge(award, icon)) }
                )
            }
        )
    }
}