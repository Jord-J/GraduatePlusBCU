package screens.account.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import common.components.Profile
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseException
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.storage.StorageReference
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel : ScreenModel {
    var uiState by mutableStateOf(EditUiState())
        private set

    var bioTextFieldValue: TextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbUser: DatabaseReference
    private lateinit var dbStorage: StorageReference

    fun fetchData() {
        screenModelScope.launch {
            auth = Firebase.auth
            dbUser = Firebase.database.reference("Users")

            uiState = uiState.copy(isLoading = true)

            val uid = auth.currentUser?.uid

            uiState = if (uid != null) {
                val currentProfile = Profile(
                    name = dbUser.child(uid).child("name").valueEvents.first().value.toString(),
                    email = dbUser.child(uid).child("email").valueEvents.first().value.toString(),
                    bio = dbUser.child(uid).child("bio").valueEvents.first().value.toString(),
                    badgeCount = dbUser.child(uid)
                        .child("badges").valueEvents.first().value.hashCode(),
                    eventCount = dbUser.child(uid)
                        .child("events").valueEvents.first().value.hashCode(),
                    imageUrl = dbUser.child(uid)
                        .child("imageUrl").valueEvents.first().value.toString()
                )
                uiState.copy(
                    isLoading = false,
                    profile = currentProfile
                )
            } else {
                uiState.copy(errorMessage = "Failed loading profile")
            }
        }
    }

    fun upload() {
        screenModelScope.launch {
            auth = Firebase.auth
            dbUser = Firebase.database.reference("Users")
            dbStorage = Firebase.storage.reference("Users")

            uiState = uiState.copy(isLoading = true)

            val uid = auth.currentUser?.uid

            try {
                if (uid != null) {

                    if (bioTextFieldValue.text.isNotEmpty()) {
                        dbUser.child(uid).child("bio").setValue(bioTextFieldValue.text)
                    }

                    if (uiState.password.isNotEmpty()) {
                        auth.currentUser?.updatePassword(uiState.password)
                    }

                    dbUser.child(uid).child("name").setValue(uiState.profile?.name)

                    uiState = uiState.copy(uploadSucceed = true)

                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = "failed"
                    )
                }
            } catch (e: FirebaseException) {
                uiState = uiState.copy(errorMessage = e.message, isLoading = false)
            } catch (e: FirebaseNetworkException) {
                uiState = uiState.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }

    fun onBioChange(inputBio: TextFieldValue) {
        bioTextFieldValue = bioTextFieldValue.copy(
            text = inputBio.text,
            selection = TextRange(index = inputBio.text.length)
        )
    }

    fun onNameChange(inputName: String) {
        uiState = uiState.copy(
            profile = uiState.profile?.copy(name = inputName)
        )
    }

    fun onProfilePictureChange(image: ImageBitmap) {
        uiState = uiState.copy(
            profileImage = image
        )
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(password = input)
    }
}

data class EditUiState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val uploadSucceed: Boolean = false,
    val errorMessage: String? = null,
    val profileImage: ImageBitmap? = null,
    var password: String = ""
)