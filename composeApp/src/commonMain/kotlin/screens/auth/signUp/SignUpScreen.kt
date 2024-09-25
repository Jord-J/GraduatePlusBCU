package screens.auth.signUp

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import graduateplusbcu.composeapp.generated.resources.name_hint
import graduateplusbcu.composeapp.generated.resources.password_hint
import graduateplusbcu.composeapp.generated.resources.signup_button
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
    onSignUpClick: () -> Unit,
    onNavigationToHome: () -> Unit
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
                .verticalScroll(rememberScrollState())
                .background(
                    MaterialTheme.colors.surface
                )
                .padding(
                    top = ExtraLargeSpacing + LargeSpacing,
                    start = LargeSpacing + MediumSpacing,
                    end = LargeSpacing + MediumSpacing,
                    bottom = LargeSpacing
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {
            Image(
                modifier = modifier.height(100.dp).width(400.dp),
                painter = painterResource(resource = Res.drawable.gplusbanner),
                contentDescription = null
            )

            CustomTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                hint = Res.string.name_hint
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
                    onSignUpClick()
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
                Text(text = stringResource(resource = Res.string.signup_button))
            }

            GoToLogin {
                onNavigateToLogin()
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
        key1 = uiState.uploadSucceeded,
        block = {
            if (uiState.uploadSucceeded) {
                onNavigationToHome()
            }
        }
    )
}

@Composable
fun GoToLogin(
    modifier: Modifier = Modifier,
    onNavigationToLogin: () -> Unit
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(
            SmallSpacing
        )
    ) {
        Text(text = "Have an account already?", style = MaterialTheme.typography.body1)
        Text(
            text = "Login",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primary,
            modifier = modifier.clickable { onNavigationToLogin() }
        )
    }
}