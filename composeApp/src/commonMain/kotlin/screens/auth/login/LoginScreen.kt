package screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import common.components.CustomTextField
import common.theming.ButtonHeight
import common.theming.ExtraLargeSpacing
import common.theming.LargeSpacing
import common.theming.MediumSpacing
import common.theming.SmallSpacing
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.email_hint
import graduateplusbcu.composeapp.generated.resources.gplusbanner
import graduateplusbcu.composeapp.generated.resources.login_button
import graduateplusbcu.composeapp.generated.resources.password_hint
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
// MVVM: View
fun LoginScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onNavigationToSignUp: () -> Unit,
    onNavigationToHome: () -> Unit,
    onNavigationToAdmin: () -> Unit,
    onNavigationToResetPassword: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
                .padding(
                    top = ExtraLargeSpacing + LargeSpacing,
                    start = LargeSpacing + MediumSpacing,
                    end = LargeSpacing + MediumSpacing,
                    bottom = LargeSpacing
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LargeSpacing)
        )
        {
            Image(
                modifier = modifier.height(100.dp).width(400.dp),
                painter = painterResource(resource = Res.drawable.gplusbanner),
                contentDescription = null
            )

            CustomTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                hint = Res.string.email_hint,
                keyboardType = KeyboardType.Email
            )

            CustomTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                hint = Res.string.password_hint,
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true
            )

            Button(
                onClick = {
                    onSignInClick()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 0.dp
                ),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(4282061423),
                    contentColor = Color(-1)
                )
            ) {
                Text(text = stringResource(resource = Res.string.login_button))
            }

            GoToSignUp {
                onNavigationToSignUp()
            }

            GoToChangePassword {
                onNavigationToResetPassword()
            }

            if (uiState.authErrorMessage != null) {
                Text(text = uiState.authErrorMessage.toString(), color = Color.Red)
            }

            if (uiState.isAuthenticating) {
                CircularProgressIndicator()
            }
        }
    }

    LaunchedEffect(
        key1 = uiState.authenticationSucceeded,
        block = {
            if (uiState.authenticationSucceeded && uiState.admin) {
                onNavigationToAdmin()
            } else if (uiState.authenticationSucceeded) {
                onNavigationToHome()
            }
        }
    )
}

// Custom component to make: sign up - Clickable
@Composable
fun GoToSignUp(
    modifier: Modifier = Modifier,
    onNavigationToSignUp: () -> Unit
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(
            SmallSpacing
        )
    ) {
        Text(text = "Don't have an account?", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Sign up",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.clickable { onNavigationToSignUp() }
        )
    }
}

// Custom component to make: change password - Clickable
@Composable
fun GoToChangePassword(
    modifier: Modifier = Modifier,
    onNavigationToResetPassword: () -> Unit
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(
            SmallSpacing
        )
    ) {
        Text(
            text = "Forgot password?",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Reset password",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.clickable { onNavigationToResetPassword() }
        )
    }
}