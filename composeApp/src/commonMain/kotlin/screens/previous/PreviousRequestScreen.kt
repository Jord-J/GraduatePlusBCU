package screens.previous

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.components.CustomActivityTextField
import common.theming.LargeSpacing
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.bculogo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PreviousRequestScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: PreviousRequestUiState,
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
        Column(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = modifier
                    .padding(LargeSpacing)
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(LargeSpacing)
            ) {
                uiState.previous?.let {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.bculogo),
                        contentDescription = null
                    )
                    Text(
                        text = "Birmingham City University",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Canvas(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    drawRect(
                        color = Color.LightGray,
                        size = Size(size.width, 3f)
                    )
                }

                uiState.previous?.name?.let {
                    Text(
                        text = "Submitted by: $it",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                uiState.previous?.email?.let {
                    Text(
                        text = "Email: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                uiState.previous?.status?.let {
                    Text(
                        text = "Status: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (it == "Accepted") {
                            Color.Green
                        } else {
                            Color.Red
                        }
                    )
                }

                uiState.previous?.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                CustomActivityTextField(
                    modifier = modifier,
                    value = uiState.activityTextValue,
                    onValueChange = {},
                    readOnly = true
                )
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