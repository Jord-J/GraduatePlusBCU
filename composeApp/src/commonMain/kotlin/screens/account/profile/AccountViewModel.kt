package screens.account.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import common.components.Profile
import common.components.ProfileActivity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AccountViewModel : ScreenModel {
    var uiState by mutableStateOf(AccountUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbUser: DatabaseReference

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

                val badges = ProfileActivity(
                    bronzeAchieved = dbUser.child(uid)
                        .child("bronzeAwardAchieved").valueEvents.first().value.hashCode(),
                    silverAchieved = dbUser.child(uid)
                        .child("silverAwardAchieved").valueEvents.first().value.hashCode(),
                    goldAchieved = dbUser.child(uid)
                        .child("goldAwardAchieved").valueEvents.first().value.hashCode(),
                    platinumAchieved = dbUser.child(uid)
                        .child("platinumAwardAchieved").valueEvents.first().value.hashCode(),
                )
                uiState.copy(
                    isLoading = false,
                    profile = currentProfile,
                    badges = badges
                )
            } else {
                uiState.copy(errorMessage = "Failed loading profile")
            }
        }
    }

    fun signOut() {
        screenModelScope.launch {
            Firebase.auth.signOut()
            uiState = uiState.copy(signedOut = true)
        }
    }
}

data class AccountUiState(
    val isLoading: Boolean = true,
    val profile: Profile? = null,
    val badges: ProfileActivity? = null,
    var errorMessage: String? = null,
    var badgeCount: Int? = 0,
    var eventCount: Int? = 0,
    var imageUrl: String? = null,
    var signedOut: Boolean = false
)