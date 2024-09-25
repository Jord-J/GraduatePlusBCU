package screens.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import common.components.EventData
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SelectedEventViewModel : ScreenModel {
    var uiState by mutableStateOf(SelectedEventUiState())
        private set

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var dbEnrolled: DatabaseReference
    private lateinit var dbUserEventCount: DatabaseReference

    init {
        if (auth.currentUser == null) {
            uiState = uiState.copy(loggedIn = false)
        }
    }

    fun fetchData(event: EventData, status: String) {
        screenModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val uid = auth.currentUser?.uid
            dbEnrolled = Firebase.database.reference("Users/$uid/Enrolled")
            dbUserEventCount = Firebase.database.reference("Users/$uid")

            if (uid != null) {
                for (item in dbEnrolled.valueEvents.first().children) {
                    uiState.currentEnrolledEvents.add(item.value.hashCode())
                }
            }

            event.description = event.description.replace("\\n", "\n")
            uiState =
                uiState.copy(event = event, isLoading = false, status = status)
        }
    }

    fun updateEnrolled() {
        screenModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val uid = auth.currentUser?.uid

            if (uid != null) {
                dbEnrolled.child("Event${uiState.event?.id}").setValue(uiState.event?.id?.toInt())
                dbUserEventCount.child("events")
                    .setValue(dbUserEventCount.child("events").valueEvents.first().value.hashCode() + 1)
            }
            uiState = uiState.copy(isLoading = false)
        }
    }
}

data class SelectedEventUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val loggedIn: Boolean = true,
    val event: EventData? = null,
    val status: String? = null,
    val currentEnrolledEvents: MutableList<Int> = mutableListOf(),
)