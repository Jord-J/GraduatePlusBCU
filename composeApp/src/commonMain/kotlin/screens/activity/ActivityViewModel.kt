package screens.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import common.components.Activity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class ActivityViewModel : ScreenModel {
    var uiState by mutableStateOf(ActivityUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbUser: DatabaseReference
    private lateinit var db: DatabaseReference
    private lateinit var dbRequests: DatabaseReference
    private lateinit var dbUserRequests: DatabaseReference
    private lateinit var type: String

    fun fetchData(activity: String) {
        screenModelScope.launch {

            auth = Firebase.auth

            uiState = uiState.copy(isLoading = true)

            db = Firebase.database.reference("Activities")

            for (event in db.valueEvents.first().children) {
                for (data in event.children) {
                    if (data.child("id").value.toString() == activity) {
                        type = event.key.toString().removeSuffix("Activities")
                        uiState = uiState.copy(
                            activity =
                            Activity(
                                id = data.child("id").value.toString(),
                                title = data.child("title").value.toString(),
                                description = data.child("description").value.toString()
                                    .replace("\\n", "\n"),
                                imageUrl = data.child("imageUrl").value.toString(),
                                likesCount = data.child("likes").value.hashCode()
                            )
                        )
                    }
                }
            }

            uiState = uiState.copy(isLoading = false)
        }
    }

    fun updateActivityText(input: String) {
        uiState = uiState.copy(
            activityTextValue = input
        )
    }

    fun onSubmit() {
        screenModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            auth = Firebase.auth
            val uid = auth.currentUser?.uid
            dbUser = Firebase.database.reference("Users/$uid")
            dbRequests = Firebase.database.reference("Requests")
            dbUserRequests = Firebase.database.reference("Users/$uid/Requests")
            var count = 1

            for (request in dbRequests.valueEvents.first().children) count += 1

            val request = Request(
                activityTitle = uiState.activity?.title,
                activityType = type,
                name = dbUser.child("name").valueEvents.first().value.toString(),
                email = dbUser.child("email").valueEvents.first().value.toString(),
                date = "04/04/2024",
                submission = uiState.activityTextValue,
                description = uiState.activity?.description,
                uid = uid,
                activityId = uiState.activity?.id
            )
            dbRequests.child(count.toString()).setValue(request)
            dbUserRequests.child("$count").setValue(uiState.activity?.title)

            uiState = uiState.copy(isLoading = false, submissionSucceeded = true)
        }
    }
}

data class ActivityUiState(
    val isLoading: Boolean = true,
    val activity: Activity? = null,
    val activityTextValue: String = "",
    val submissionSucceeded: Boolean = false
)

@Serializable
data class Request(
    val activityTitle: String? = null,
    val activityType: String? = null,
    val name: String? = null,
    val email: String? = null,
    val date: String? = null,
    val submission: String? = null,
    val completed: Int = 0,
    val description: String? = null,
    val uid: String? = null,
    val activityId: String? = null
)