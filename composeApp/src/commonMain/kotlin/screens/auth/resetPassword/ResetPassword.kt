package screens.auth.resetPassword

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.resetPassword_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.auth.login.Login

class ResetPassword : Screen {
    private val viewModel = ResetPasswordViewModel()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CustomAppBar(
                    stringResource(Res.string.resetPassword_page),
                    navigator,
                    Login()
                )
            },
            content = {
                ResetPasswordScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onEmailChange = viewModel::updateEmail,
                    onNavigationToLogin = {
                        navigator.pop()
                    },
                    onResetPasswordClick = viewModel::resetPassword
                )
            }
        )
    }
}