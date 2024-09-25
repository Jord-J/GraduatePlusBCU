package screens.activities

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

class ActivitiesViewModel : ScreenModel {
    var uiState by mutableStateOf(ActivitiesUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbUserActivities: DatabaseReference
    private lateinit var dbActivities: DatabaseReference
    private lateinit var dbLikes: DatabaseReference
    private lateinit var dbActivitiesCount: DatabaseReference
    private lateinit var dbSubmitted: DatabaseReference
    private lateinit var type: String

    fun fetchData(activitiesId: String) {
        screenModelScope.launch {
            type = activitiesId

            auth = Firebase.auth

            uiState = uiState.copy(isLoading = true)

            val uid = auth.currentUser?.uid

            dbActivities = Firebase.database.reference("Activities/$activitiesId")
            dbLikes = Firebase.database.reference("Users/$uid/Likes")
            dbUserActivities = Firebase.database.reference("Users/$uid/Activities")
            dbActivitiesCount = Firebase.database.reference("Users")
            dbSubmitted = Firebase.database.reference("Users/$uid/Requests")

            if (uid != null) {
                for (event in dbLikes.valueEvents.first().children) {
                    uiState.likedActivities.add(event.value.hashCode())
                }

                for (event in dbUserActivities.valueEvents.first().children) {
                    uiState.currentActivities.add(event.value.hashCode())
                }

                for (event in dbSubmitted.valueEvents.first().children) {
                    uiState.submittedActivities.add(event.value.toString())
                }

                uiState = uiState.copy(
                    activityCount = dbActivitiesCount.child(uid)
                        .child(
                            type.removeSuffix("Activities").lowercase() + "Count"
                        ).valueEvents.first().value.hashCode()
                )
            }

            for (event in dbActivities.valueEvents.first().children) {
                uiState.activities.add(
                    Activity(
                        id = event.child("id").value.toString(),
                        description = event.child("description").value.toString(),
                        imageUrl = event.child("imageUrl").value.toString(),
                        likesCount = event.child("likes").value.hashCode(),
                        title = event.child("title").value.toString()
                    )
                )
            }

            uiState = uiState.copy(isLoading = false)
        }
    }

    fun onLikeClick(bool: Boolean, id: String) {
        screenModelScope.launch {
            auth = Firebase.auth

            val uid = auth.currentUser?.uid
            val activities = uiState.activities
            val reduction = when (type) {
                "BronzeActivities" -> uiState.activities[0].id.toInt()
                "SilverActivities" -> uiState.activities[0].id.toInt()
                "GoldActivities" -> uiState.activities[0].id.toInt()
                "PlatinumActivities" -> uiState.activities[0].id.toInt()
                else -> {
                    0
                }
            }

            dbActivities =
                Firebase.database.reference("Activities/$type/Activity${(id.toInt() - reduction) + 1}")

            dbLikes = Firebase.database.reference("Users/$uid/Likes")

            if (bool) {
                activities[id.toInt() - reduction].likesCount += 1
                uiState = uiState.copy(activities = activities)

                dbActivities.child("likes").setValue(activities[id.toInt() - reduction].likesCount)
                dbLikes.child("Activity$id").setValue(id.toInt().hashCode())
            } else {
                activities[id.toInt() - reduction].likesCount -= 1
                uiState = uiState.copy(activities = activities)

                dbActivities.child("likes").setValue(activities[id.toInt() - reduction].likesCount)
                dbLikes.child("Activity$id").removeValue()
            }
        }
    }
}

data class ActivitiesUiState(
    val debug: String? = null,
    val isLoading: Boolean = true,
    val activitySection: String? = null,
    val activities: MutableList<Activity> = mutableListOf(),
    val currentActivities: MutableList<Int> = mutableListOf(),
    val likedActivities: MutableList<Int> = mutableListOf(),
    val submittedActivities: MutableList<String> = mutableListOf(),
    val activityCount: Int? = null,
    val errorMessage: String? = null
)