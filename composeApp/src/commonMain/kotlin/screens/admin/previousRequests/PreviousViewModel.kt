package screens.admin.previousRequests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import common.components.Previous
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PreviousViewModel : ScreenModel {
    var uiState by mutableStateOf(PreviousUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbActive: DatabaseReference

    init {
        fetchActive()
    }

    private fun fetchActive() {
        screenModelScope.launch {
            auth = Firebase.auth

            dbActive = Firebase.database.reference("Requests")

            for (event in dbActive.valueEvents.first().children) {
                if (event.child("completed").value.hashCode() == 1) {
                    uiState.previousList.add(
                        Previous(
                            id = event.key.toString(),
                            title = event.child("activityTitle").value.toString(),
                            name = event.child("name").value.toString(),
                            email = event.child("email").value.toString(),
                            activityType = event.child("activityType").value.toString(),
                            submission = event.child("submission").value.toString(),
                            description = event.child("description").value.toString(),
                            uid = event.child("uid").value.toString(),
                            activityId = event.child("activityId").value.toString(),
                            status = event.child("status").value.toString()
                        )
                    )
                }
            }

            uiState = uiState.copy(isLoading = false)
        }
    }
}

data class PreviousUiState(
    val isLoading: Boolean = true,
    val previousList: MutableList<Previous> = mutableListOf(),
    val errorMessage: String? = null
)