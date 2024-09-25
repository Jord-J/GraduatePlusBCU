package screens.badge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BadgeViewModel : ScreenModel {
    var uiState by mutableStateOf(BadgeUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbUser: DatabaseReference

    fun fetchData(award: String) {
        screenModelScope.launch {
            auth = Firebase.auth

            uiState = uiState.copy(isLoading = true)

            val uid = auth.currentUser?.uid

            dbUser = Firebase.database.reference("Users/$uid")

            if (uid != null) {
                uiState = uiState.copy(
                    name = dbUser.child("name").valueEvents.first().value.toString(),
                    date = dbUser.child(award + "AwardAchievedDate").valueEvents.first().value.toString()
                )
            }

            uiState = uiState.copy(isLoading = false)
        }
    }
}

data class BadgeUiState(
    var isLoading: Boolean = true,
    var name: String? = null,
    var date: String? = null
)