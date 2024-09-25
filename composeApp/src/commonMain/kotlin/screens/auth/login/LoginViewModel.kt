package screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.FirebaseTooManyRequestsException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel : ScreenModel {
    var uiState by mutableStateOf(LoginUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var user: DatabaseReference

    fun signIn() {
        screenModelScope.launch {
            auth = Firebase.auth
            uiState = uiState.copy(isAuthenticating = true, authErrorMessage = null)

            if (uiState.email.isEmpty() || uiState.password.isEmpty()) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = "Field(s) are empty."
                )
            } else {
                uiState = try {
                    auth.signInWithEmailAndPassword(
                        uiState.email,
                        uiState.password
                    )

                    val uid = auth.currentUser?.uid
                    user = Firebase.database.reference("Users/$uid")

                    if (user.child("accountType").valueEvents.first().value == "admin") {
                        uiState = uiState.copy(
                            admin = true
                        )
                    }

                    uiState.copy(
                        isAuthenticating = false,
                        authenticationSucceeded = true
                    )
                } catch (e: FirebaseAuthException) {
                    uiState.copy(
                        isAuthenticating = false,
                        authErrorMessage = e.message
                    )
                } catch (e: FirebaseNetworkException) {
                    uiState.copy(
                        isAuthenticating = false,
                        authErrorMessage = "Check your internet connection."
                    )
                } catch (e: FirebaseTooManyRequestsException) {
                    uiState.copy(
                        isAuthenticating = false,
                        authErrorMessage = "Too many requests, try again later."
                    )
                }
            }
        }
    }

    fun updateEmail(input: String) {
        uiState = uiState.copy(
            email = input
        )
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(
            password = input
        )
    }
}

data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceeded: Boolean = false,
    var admin: Boolean = false
)

