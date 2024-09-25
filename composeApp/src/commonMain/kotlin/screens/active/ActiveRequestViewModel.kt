package screens.active

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import common.components.Active
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseException
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class ActiveRequestViewModel : ScreenModel {
    var uiState by mutableStateOf(ActiveRequestUiState())
        private set

    private var idState by mutableStateOf(ActivityIdInt())

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var dbRequests: DatabaseReference
    private lateinit var dbUserRequests: DatabaseReference
    private lateinit var dbUser: DatabaseReference
    private lateinit var dbUserActivities: DatabaseReference

    init {
        if (auth.currentUser == null) {
            uiState = uiState.copy(loggedIn = false)
        }
    }

    fun fetchData(active: Active) {
        uiState =
            uiState.copy(active = active, isLoading = false, activityTextValue = active.submission)
        idState = idState.copy(id = uiState.active?.activityId?.toInt())
    }

    fun onButtonClick(bool: Boolean) {
        try {
            screenModelScope.launch {
                dbRequests = Firebase.database.reference("Requests/${uiState.active?.id}")
                dbUserRequests =
                    Firebase.database.reference("Users/${uiState.active?.uid}/Requests")
                dbUser = Firebase.database.reference("Users/${uiState.active?.uid}")
                dbUserActivities =
                    Firebase.database.reference("Users/${uiState.active?.uid}/Activities")

                if (bool) {
                    dbRequests.child("completed").setValue(1)
                    dbRequests.child("status").setValue("Accepted")
                    var count = dbUser.child(
                        uiState.active?.activityType.toString().lowercase() + "Count"
                    ).valueEvents.first().value.hashCode()
                    var badgeCount = dbUser.child("badges").valueEvents.first().value.hashCode()
                    if (count == 3) {
                        count++
                        badgeCount++
                        dbUser.child(uiState.active?.activityType.toString().lowercase() + "Count")
                            .setValue(count)
                        dbUser.child(
                            uiState.active?.activityType.toString().lowercase() + "AwardAchieved"
                        ).setValue(1)
                        dbUser.child("badges").setValue(badgeCount)
                        dbUser.child(
                            uiState.active?.activityType.toString()
                                .lowercase() + "AwardAchievedDate"
                        ).setValue(
                            GMTDate().dayOfMonth.toString() + " " + GMTDate().month.toString() + " " + GMTDate().year.toString()
                        )
                    } else {
                        count++
                        dbUser.child(uiState.active?.activityType.toString().lowercase() + "Count")
                            .setValue(count)
                    }
                    dbUserActivities.child("Activity${idState.id}")
                        .setValue(idState.id.hashCode())
                    dbUserRequests.child(uiState.active?.id.toString()).removeValue()
                } else {
                    dbRequests.child("completed").setValue(1)
                    dbRequests.child("status").setValue("Denied")
                    dbUserRequests.child(uiState.active?.id.toString()).removeValue()
                }

                uiState = uiState.copy(actionCompleted = true)
            }
        } catch (e: DatabaseException) {
            uiState = uiState.copy(errorMessage = "Check your network connection")
        }
    }
}

data class ActiveRequestUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val loggedIn: Boolean = true,
    val actionCompleted: Boolean = false,
    val active: Active? = null,
    val activityTextValue: String = "",
)

@Serializable
data class ActivityIdInt(
    val id: Int? = null
)