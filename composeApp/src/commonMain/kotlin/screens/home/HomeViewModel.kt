package screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import common.components.ProfileActivity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel : ScreenModel {
    var uiState by mutableStateOf(HomeUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbUser: DatabaseReference
    private lateinit var currentProfile: ProfileActivity

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        screenModelScope.launch {
            auth = Firebase.auth

            dbUser = Firebase.database.reference("Users")

            val uid = auth.currentUser?.uid

            if (uid != null) {
                try {
                    currentProfile = ProfileActivity(
                        bronzeAchieved = dbUser.child(uid)
                            .child("bronzeAwardAchieved").valueEvents.first().value.hashCode(),
                        silverAchieved = dbUser.child(uid)
                            .child("silverAwardAchieved").valueEvents.first().value.hashCode(),
                        goldAchieved = dbUser.child(uid)
                            .child("goldAwardAchieved").valueEvents.first().value.hashCode(),
                        platinumAchieved = dbUser.child(uid)
                            .child("platinumAwardAchieved").valueEvents.first().value.hashCode(),
                    )

                    uiState = uiState.copy(
                        isLoading = false,
                        profile = currentProfile
                    )
                } catch (e: FirebaseException) {
                    uiState = uiState.copy(errorMessage = e.message)
                }
            } else {
                uiState = uiState.copy(errorMessage = "Failed loading profile")
            }
        }
    }
}

data class HomeUiState(
    var isLoading: Boolean = true,
    var profile: ProfileActivity? = null,
    var errorMessage: String? = null,
    var badgeCount: Int? = 0,
    var eventCount: Int? = 0,
    var imageUrl: String? = null
)