package common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theming.LargeSpacing
import common.theming.MediumElevation
import common.theming.MediumSpacing
import common.theming.SmallElevation
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.bculogo
import graduateplusbcu.composeapp.generated.resources.profile_placeholder
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActivityItem(
    modifier: Modifier = Modifier,
    activity: Activity,
    onActivityClick: (Activity) -> Unit,
    onLikeClick: (Boolean, String) -> Unit,
    currentAchievedActivities: MutableList<Int>,
    currentLikedActivities: MutableList<Int>,
    currentSubmittedActivities: MutableList<String>,
    likeCount: Int
) {
    var obtained by remember {
        mutableStateOf(false)
    }
    var submitted = false
    var clickable = true
    var currentlyLiked by remember {
        mutableStateOf(false)
    }

    for (id in currentAchievedActivities) {
        if (id.toString() == activity.id) {
            clickable = false
            obtained = true
        }
    }

    for (id in currentLikedActivities) {
        if (id.toString() == activity.id) {
            currentlyLiked = true
        }
    }

    for (title in currentSubmittedActivities) {
        if (title == activity.title) {
            clickable = false
            submitted = true
        }
    }

    var state by remember {
        mutableStateOf(currentlyLiked)
    }

    var likes by remember { mutableIntStateOf(likeCount) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(4293519853))
            .padding(top = 15.dp, bottom = 15.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(0.9f)
                    .shadow(MediumElevation)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .conditional(clickable) {
                        clickable { onActivityClick(activity) }
                    }
            ) {

                ActivityHeader(
                    name = activity.title
                )

                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    KamelImage(
                        resource = asyncPainterResource(activity.imageUrl),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .aspectRatio(ratio = 1.0f),
                        contentScale = ContentScale.Crop,
                        onLoading = { CircularProgressIndicator() },
                        onFailure = {
                            Image(
                                painterResource(resource = Res.drawable.profile_placeholder),
                                contentDescription = null
                            )
                        },
                        alpha = if (!clickable) {
                            0.2f
                        } else {
                            1.0f
                        }
                    )

                    if (obtained) {
                        Text(
                            text = "Completed",
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    if (submitted) {
                        Text(
                            text = "Submitted",
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                state = !state

                                if (state) {
                                    likes += 1
                                } else {
                                    likes -= 1
                                }

                                onLikeClick(state, activity.id)
                            }
                        ) {
                            Icon(
                                imageVector = if (state) {
                                    Icons.Filled.ThumbUp
                                } else {
                                    Icons.Outlined.ThumbUp
                                },
                                contentDescription = null
                            )
                        }

                        CountText(
                            count = likes
                        )
                    }

                    if (obtained) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .shadow(
                                    elevation = SmallElevation,
                                    shape = RoundedCornerShape(percent = 50)
                                )
                                .background(
                                    color = Color(4282093586),
                                    shape = CircleShape
                                )
                                .size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActivityHeader(
    modifier: Modifier = Modifier,
    name: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LargeSpacing,
                vertical = MediumSpacing
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Image(
            painter = painterResource(Res.drawable.bculogo),
            contentDescription = null
        )
    }
}

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

@Composable
fun CountText(
    count: Int
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            ) {
                append(text = "$count")
            }
        }
    )
}