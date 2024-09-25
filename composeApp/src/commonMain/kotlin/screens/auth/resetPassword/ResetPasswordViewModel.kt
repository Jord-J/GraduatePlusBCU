package screens.auth.resetPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class ResetPasswordViewModel : ScreenModel {
    var uiState by mutableStateOf(ResetPasswordUIState())
        private set

    private lateinit var auth: FirebaseAuth

    fun resetPassword() {
        screenModelScope.launch {
            auth = Firebase.auth
            uiState = uiState.copy(isAuthenticating = true)

            if (uiState.email.isEmpty()) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = "Field(s) are empty!"
                )
            } else {
                try {
                    auth.sendPasswordResetEmail(uiState.email)

                    uiState = uiState.copy(
                        isAuthenticating = false,
                        authenticationSucceeded = true
                    )
                } catch (e: FirebaseNetworkException) {
                    uiState = uiState.copy(
                        isAuthenticating = false,
                        authErrorMessage = "Check your internet connection"
                    )
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    uiState = uiState.copy(
                        isAuthenticating = false,
                        authErrorMessage = "Email does not exist."
                    )
                }
            }
        }
    }

    fun updateEmail(input: String) {
        uiState = uiState.copy(email = input)
    }
}

data class ResetPasswordUIState(
    var email: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceeded: Boolean = false
)