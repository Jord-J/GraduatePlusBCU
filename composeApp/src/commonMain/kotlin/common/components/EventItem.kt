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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import common.theming.MediumElevation
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.profile_placeholder
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.util.date.GMTDate
import io.ktor.util.date.Month
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EventItem(
    modifier: Modifier = Modifier,
    event: EventData,
    onEventClick: (EventData, String) -> Unit,
    onLikeClick: (Boolean, String) -> Unit,
    currentLikedEvents: MutableList<Int>,
    likeCount: Int
) {
    var currentlyLiked by remember {
        mutableStateOf(false)
    }

    for (id in currentLikedEvents) {
        if (id.toString() == event.id) {
            currentlyLiked = true
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
                    .clickable { onEventClick(event, parseDate(event.date)) }
            ) {

                ActivityHeader(
                    name = event.title
                )

                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    KamelImage(
                        resource = asyncPainterResource(event.imageUrl),
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
                        }
                    )
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

                                onLikeClick(state, event.id)
                            }
                        ) {
                            Icon(
                                imageVector = if (state) {
                                    Icons.Filled.ThumbUp
                                } else {
                                    Icons.Outlined.ThumbUp
                                },
                                contentDescription = null,
                            )
                        }

                        CountText(
                            count = likes
                        )
                    }

                    Row(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = null
                        )
                        Text(
                            text = parseDate(event.date),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

fun parseDate(eventDate: String): String {
    val startMonth = getMonth(eventDate.substring(2, 4))

    // If 1, means time has elapsed
    val startHasPassed = GMTDate(
        seconds = 0,
        minutes = 0,
        hours = 0, // Minute one as we're GMT+1
        dayOfMonth = eventDate.substring(0, 2).toInt(),
        month = startMonth,
        year = eventDate.takeLast(4).toInt()
    )
        .let {
            GMTDate().compareTo(
                it
            )
        }

    // If 1, means time has elapsed
    val endHasPassed = GMTDate(
        seconds = 0,
        minutes = 0,
        hours = 0, // Minute one as we're GMT+1
        dayOfMonth = eventDate.substring(9, 11).toInt(),
        month = startMonth,
        year = eventDate.takeLast(4).toInt()
    )
        .let {
            GMTDate().compareTo(
                it
            )
        }

    return if (endHasPassed == 1) {
        "Event Finished"
    } else if (startHasPassed == 1) {
        "In Progress"
    } else {
        val sb = StringBuilder(eventDate.take(8))
        sb.insert(2, "/")
        sb.insert(5, "/")
        return sb.toString()
    }
}

fun getMonth(month: String): Month {
    val monthAsMonthType = when (month) {
        "01" -> Month.JANUARY
        "02" -> Month.FEBRUARY
        "03" -> Month.MARCH
        "04" -> Month.APRIL
        "05" -> Month.MAY
        "06" -> Month.JUNE
        "07" -> Month.JULY
        "08" -> Month.AUGUST
        "09" -> Month.SEPTEMBER
        "10" -> Month.OCTOBER
        "11" -> Month.NOVEMBER
        "12" -> Month.DECEMBER
        else -> {
            null
        }
    }
    return monthAsMonthType as Month
}
