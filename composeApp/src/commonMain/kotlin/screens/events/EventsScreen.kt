package screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import common.components.EventData
import common.components.EventItem

@Composable
fun EventsScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: EventsUiState,
    onEventClick: (EventData, String) -> Unit,
    onLikeClick: (Boolean, String) -> Unit,
    fetchData: () -> Unit,
) {
    var initialApiCalled by rememberSaveable {
        mutableStateOf(
            false
        )
    }

    if (uiState.isLoading) {
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(4293519853))
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(4293519853))
            ) {
                items(items = uiState.events, key = { event -> event.id }) {
                    EventItem(
                        event = it,
                        onEventClick = onEventClick,
                        onLikeClick = onLikeClick,
                        currentLikedEvents = uiState.likedEvents,
                        likeCount = it.likesCount
                    )
                }
            }
        }
    }

    if (!initialApiCalled) {
        LaunchedEffect(Unit) {
            fetchData()
            initialApiCalled = true
        }
    }
}