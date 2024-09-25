package screens.auth.resetPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.email_hint
import graduateplusbcu.composeapp.generated.resources.gplusbanner
import graduateplusbcu.composeapp.generated.resources.reset_button
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ResetPasswordScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: ResetPasswordUIState,
    onEmailChange: (String) -> Unit,
    onNavigationToLogin: () -> Unit,
    onResetPasswordClick: () -> Unit,
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
                .background(MaterialTheme.colorScheme.surface)
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
                value = uiState.email,
                onValueChange = onEmailChange,
                hint = Res.string.email_hint,
                keyboardType = KeyboardType.Email
            )

            Button(
                onClick = {
                    onResetPasswordClick()
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
                Text(text = stringResource(resource = Res.string.reset_button))
            }

            if (uiState.isAuthenticating) {
                CircularProgressIndicator()
            }

            if (uiState.authErrorMessage != null) {
                Text(text = uiState.authErrorMessage.toString(), color = Color.Red)
            }
        }
    }

    LaunchedEffect(
        key1 = uiState.authenticationSucceeded,
        block = {
            if (uiState.authenticationSucceeded) {
                onNavigationToLogin()
            }
        }
    )
}