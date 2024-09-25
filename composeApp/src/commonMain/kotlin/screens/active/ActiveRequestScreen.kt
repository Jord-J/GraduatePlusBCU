package screens.active

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import common.theming.ButtonHeight
import common.theming.LargeSpacing
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.bculogo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActiveRequestScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: ActiveRequestUiState,
    fetchData: () -> Unit,
    onButtonClick: (Boolean) -> Unit,
    goToPrevious: () -> Unit,
    goToLogin: () -> Unit
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
                uiState.active?.let {
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

                uiState.active?.name?.let {
                    Text(
                        text = "Submitted by: $it",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                uiState.active?.email?.let {
                    Text(
                        text = "Email: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                uiState.active?.description?.let {
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

                Button(
                    onClick = {
                        onButtonClick(true)
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(ButtonHeight),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 0.dp
                    ),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 74, green = 191, blue = 17),
                        contentColor = Color(-1)
                    )
                ) {
                    Text(text = "Accept")
                }

                Button(
                    onClick = {
                        onButtonClick(false)
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(ButtonHeight),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 0.dp
                    ),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 255, green = 0, blue = 0),
                        contentColor = Color(-1)
                    )
                ) {
                    Text(text = "Deny")
                }

                if (uiState.errorMessage != null) {
                    Text(text = uiState.errorMessage.toString(), color = Color.Red)
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

    LaunchedEffect(
        key1 = uiState.actionCompleted,
        key2 = uiState.errorMessage,
        key3 = uiState.loggedIn,
        block = {
            if (uiState.actionCompleted) {
                goToPrevious()
            }

            if (!uiState.loggedIn) {
                goToLogin()
            }
        }
    )
}