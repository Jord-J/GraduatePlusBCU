package screens.activities

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.components.Activity
import common.components.ActivityItem
import common.theming.LargeSpacing
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActivitiesScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: ActivitiesUiState,
    onActivityClick: (Activity) -> Unit,
    onLikeClick: (Boolean, String) -> Unit,
    fetchData: () -> Unit,
    type: String,
    icon: DrawableResource
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
            Column(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(LargeSpacing),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Progress", style = MaterialTheme.typography.titleLarge)

                    LinearProgressIndicator(
                        modifier = modifier
                            .clip(RoundedCornerShape(50))
                            .height(20.dp),
                        color = Color(red = 144, green = 238, blue = 144),
                        trackColor = Color(red = 205, green = 248, blue = 205),
                        progress = {
                            uiState.activityCount?.toFloat()?.div(4) ?: 0f
                        })

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.54f),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 28.sp
                                )
                            ) {
                                append(
                                    if (uiState.activityCount!! >= 4) {
                                        "4"
                                    } else {
                                        uiState.activityCount.toString()
                                    }
                                )
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            ) {
                                append(text = " /4")
                            }
                        }
                    )
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(LargeSpacing),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Achievement:",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Image(
                        painter = painterResource(resource = icon),
                        contentDescription = null,
                        modifier = modifier.size(52.dp)
                    )

                    Text(
                        text = "${type.removeSuffix("Activities")} Award",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color(4293519853))
                ) {
                    items(items = uiState.activities, key = { activity -> activity.id }) {
                        ActivityItem(
                            activity = it,
                            onActivityClick = onActivityClick,
                            onLikeClick = onLikeClick,
                            currentAchievedActivities = uiState.currentActivities,
                            currentLikedActivities = uiState.likedActivities,
                            likeCount = it.likesCount,
                            currentSubmittedActivities = uiState.submittedActivities
                        )
                    }
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
