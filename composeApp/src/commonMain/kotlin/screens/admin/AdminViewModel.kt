package screens.admin

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

class AdminViewModel : ScreenModel {
    var uiState by mutableStateOf(AdminUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRequests: DatabaseReference

    init {
        fetchData()
    }

    fun fetchData() {
        screenModelScope.launch {
            auth = Firebase.auth

            dbRequests = Firebase.database.reference("Requests")

            for (event in dbRequests.valueEvents.first().children) {
                if (event.child("completed").value.hashCode() == 0) {
                    uiState.activeRequests++
                } else {
                    uiState.previousRequests++
                }
            }

            uiState = uiState.copy(isLoading = false)
        }
    }
}

data class AdminUiState(
    var isLoading: Boolean = true,
    var activeRequests: Int = 0,
    var previousRequests: Int = 0,
)
