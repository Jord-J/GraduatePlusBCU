package screens.admin.previousRequests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import common.components.Previous
import common.components.PreviousItem
import common.theming.ExtraLargeSpacing

@Composable
fun PreviousScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: PreviousUiState,
    onPreviousClick: (Previous) -> Unit
) {
    if (uiState.isLoading) {
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = Color(4293519853)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = Color(4293519853)),
            contentAlignment = Alignment.TopCenter
        ) {
            if (uiState.previousList.isEmpty()) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = (ExtraLargeSpacing * 3)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "There are no previous request(s)")
                }
            } else {
                LazyColumn {
                    items(items = uiState.previousList, key = { previous -> previous.id }) {
                        PreviousItem(
                            previous = it,
                            onPreviousClick = onPreviousClick
                        )
                    }
                }
            }
        }
    }
}