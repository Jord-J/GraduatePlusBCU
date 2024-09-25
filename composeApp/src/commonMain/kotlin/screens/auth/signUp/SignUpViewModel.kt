package screens.auth.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseException
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class SignUpViewModel : ScreenModel {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbUser: DatabaseReference

    fun signUp() {
        screenModelScope.launch {
            auth = Firebase.auth
            dbUser = Firebase.database.reference("Users")
            uiState = uiState.copy(isAuthenticating = true, authErrorMessage = null)

            if (uiState.email.isEmpty() || uiState.name.isEmpty() || uiState.password.isEmpty()) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = "Field(s) are empty!"
                )
            } else {
                uiState = try {
                    auth.createUserWithEmailAndPassword(
                        uiState.email,
                        uiState.password
                    )

                    uiState.copy(authenticationSucceeded = true)
                } catch (e: FirebaseAuthException) {
                    uiState.copy(authErrorMessage = e.message.toString(), isAuthenticating = false)
                }


                val uid = auth.currentUser?.uid

                if (uid != null && uiState.authenticationSucceeded) {
                    uiState = try {
                        dbUser.child(uid).setValue(User(uiState.name, uiState.email))
                        uiState.copy(
                            isAuthenticating = false,
                            uploadSucceeded = true
                        )
                    } catch (e: DatabaseException) {
                        uiState.copy(
                            authErrorMessage = e.message.toString(),
                            uploadSucceeded = false
                        )
                    }
                }
            }
        }
    }

    fun updateName(input: String) {
        uiState = uiState.copy(name = input)
    }

    fun updateEmail(input: String) {
        uiState = uiState.copy(email = input)
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(password = input)
    }
}

data class SignUpUiState(
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceeded: Boolean = false,
    var uploadSucceeded: Boolean = false
)

@Serializable
data class User(
    val name: String? = null,
    val email: String? = null,
    val bio: String = "",
    val badges: Int = 0,
    val events: Int = 0,
    val imageUrl: String = "",
    val bronzeAwardAchieved: Int = 0,
    val bronzeAwardAchievedDate: String = "",
    val silverAwardAchieved: Int = 0,
    val silverAwardAchievedDate: String = "",
    val goldAwardAchieved: Int = 0,
    val goldAwardAchievedDate: String = "",
    val platinumAwardAchieved: Int = 0,
    val platinumAwardAchievedDate: String = "",
    val bronzeCount: Int = 0,
    val silverCount: Int = 0,
    val goldCount: Int = 0,
    val platinumCount: Int = 0,
    val accountType: String = "user"
)