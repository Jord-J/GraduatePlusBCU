package screens.previous

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import common.components.Previous

class PreviousRequestViewModel : ScreenModel {
    var uiState by mutableStateOf(PreviousRequestUiState())
        private set

    fun fetchData(previous: Previous) {
        uiState =
            uiState.copy(
                previous = previous,
                isLoading = false,
                activityTextValue = previous.submission
            )
    }
}

data class PreviousRequestUiState(
    val isLoading: Boolean = true,
    val previous: Previous? = null,
    val activityTextValue: String = ""
)