package screens.event

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theming.LargeSpacing
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.bculogo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SelectedEventScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: SelectedEventUiState,
    fetchData: () -> Unit,
    goToLogin: () -> Unit,
    updateEnrolled: () -> Unit
) {
    var currentlyEnrolled by remember {
        mutableStateOf(false)
    }

    var initialApiCalled by rememberSaveable {
        mutableStateOf(
            false
        )
    }

    for (id in uiState.currentEnrolledEvents) {
        if (id.toString() == (uiState.event?.id ?: 0)) {
            currentlyEnrolled = true
        }
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
                uiState.event?.let {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null
                        )
                        uiState.event?.let {
                            Text(
                                text = it.location,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    when (uiState.status) {
                        "In Progress" -> {
                            OutlinedButton(
                                onClick = {},
                                modifier = modifier
                                    .padding(top = 5.dp, bottom = 5.dp, start = 2.dp, end = 2.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(red = 74, green = 191, blue = 17),
                                    contentColor = Color(-1)
                                ),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = uiState.status,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 12.sp
                                    ),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        "Event Finished" -> {
                            Button(
                                onClick = {},
                                modifier = modifier,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray,
                                    contentColor = Color.DarkGray
                                ),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = uiState.status,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 12.sp
                                    ),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        else -> {
                            Text(
                                text = "Event starts on: ${uiState.status}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp
                                ),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
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

                uiState.event?.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                if (uiState.status != "Event Finished") {
                    Button(
                        onClick = {
                            if (!currentlyEnrolled) {
                                currentlyEnrolled = true
                                updateEnrolled()
                            }
                        },
                        modifier = modifier,
                        colors = if (!currentlyEnrolled) {
                            ButtonDefaults.buttonColors(
                                containerColor = Color(red = 74, green = 191, blue = 17),
                                contentColor = Color(-1)
                            )
                        } else {
                            ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.DarkGray
                            )
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (!currentlyEnrolled) {
                                "Enroll"
                            } else {
                                "Enrolled"
                            }
                        )
                    }
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
        key1 = uiState.errorMessage,
        key2 = uiState.loggedIn,
        block = {
            if (!uiState.loggedIn) {
                goToLogin()
            }
        }
    )
}