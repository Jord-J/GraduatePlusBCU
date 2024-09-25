package screens.auth.signUp

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.signUp_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.auth.login.Login
import screens.home.Home

class SignUp : Screen {
    private val viewModel = SignUpViewModel()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { CustomAppBar(stringResource(Res.string.signUp_page), navigator, Login()) },
            content = {
                SignUpScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onNameChange = viewModel::updateName,
                    onEmailChange = viewModel::updateEmail,
                    onPasswordChange = viewModel::updatePassword,
                    onNavigateToLogin = {
                        navigator.pop()
                    },
                    onSignUpClick = viewModel::signUp,
                    onNavigationToHome = {
                        navigator.push(Home())
                    }
                )
            }
        )
    }
}