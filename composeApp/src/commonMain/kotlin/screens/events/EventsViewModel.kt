package screens.events

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

class EventsViewModel : ScreenModel {
    var uiState by mutableStateOf(EventsUiState())
        private set

    private lateinit var auth: FirebaseAuth
    private lateinit var dbEvents: DatabaseReference
    private lateinit var dbLikes: DatabaseReference

    fun fetchData() {
        screenModelScope.launch {
            auth = Firebase.auth

            uiState = uiState.copy(isLoading = true)

            val uid = auth.currentUser?.uid

            dbEvents = Firebase.database.reference("Events")
            dbLikes = Firebase.database.reference("Users/$uid/EventLikes")

            if (uid != null) {
                for (event in dbLikes.valueEvents.first().children) {
                    uiState.likedEvents.add(event.value.hashCode())
                }
            }

            for (event in dbEvents.valueEvents.first().children) {
                uiState.events.add(
                    EventData(
                        id = event.child("id").value.toString(),
                        description = event.child("description").value.toString(),
                        imageUrl = event.child("imageUrl").value.toString(),
                        likesCount = event.child("likes").value.hashCode(),
                        title = event.child("title").value.toString(),
                        date = event.child("date").value.toString(),
                        location = event.child("location").value.toString(),
                        type = event.child("type").value.toString()
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
            val events = uiState.events

            dbEvents =
                Firebase.database.reference("Events/Event$id")

            dbLikes = Firebase.database.reference("Users/$uid/EventLikes")

            if (bool) {
                events[id.toInt() - 1].likesCount += 1
                uiState = uiState.copy(events = events)

                dbEvents.child("likes").setValue(events[id.toInt() - 1].likesCount)
                dbLikes.child("Event$id").setValue(id.toInt().hashCode())
            } else {
                events[id.toInt() - 1].likesCount -= 1
                uiState = uiState.copy(events = events)

                dbEvents.child("likes").setValue(events[id.toInt() - 1].likesCount)
                dbLikes.child("Event$id").removeValue()
            }
        }
    }
}

data class EventsUiState(
    val isLoading: Boolean = true,
    val eventSection: String? = null,
    val events: MutableList<EventData> = mutableListOf(),
    val likedEvents: MutableList<Int> = mutableListOf(),
    val submittedEvents: MutableList<String> = mutableListOf(),
    val errorMessage: String? = null
)