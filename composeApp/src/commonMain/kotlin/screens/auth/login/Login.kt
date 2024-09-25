package screens.auth.login

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.login_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.admin.Admin
import screens.auth.resetPassword.ResetPassword
import screens.auth.signUp.SignUp
import screens.home.Home

// MVVM: Model
class Login : Screen {
    private val viewModel = LoginViewModel()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        // Declare the navigator
        val navigator = LocalNavigator.currentOrThrow

        // Scaffold is utilised to allow for top bar component
        Scaffold(

            // Custom topBar component
            topBar = { CustomAppBar(stringResource(Res.string.login_page), navigator, Login()) },
            content = {
                LoginScreen(
                    // it: Refers to the default padding for the topBar as we want the main content to begin
                    // below the topBar
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    onEmailChange = viewModel::updateEmail,
                    onPasswordChange = viewModel::updatePassword,
                    onNavigationToSignUp = {
                        navigator.push(SignUp())
                    },
                    onSignInClick = viewModel::signIn,
                    onNavigationToHome = {
                        navigator.push(Home())
                    },
                    onNavigationToAdmin = {
                        navigator.push(Admin())
                    },
                    onNavigationToResetPassword = {
                        navigator.push(ResetPassword())
                    }
                )
            }
        )
    }
}