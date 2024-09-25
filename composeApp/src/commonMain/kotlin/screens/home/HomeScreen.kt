package screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.theming.ExtraLargeSpacing
import common.theming.LargeSpacing
import common.theming.MediumSpacing
import common.theming.SmallElevation
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.bronzeaward
import graduateplusbcu.composeapp.generated.resources.events
import graduateplusbcu.composeapp.generated.resources.goldaward
import graduateplusbcu.composeapp.generated.resources.gplusbanner
import graduateplusbcu.composeapp.generated.resources.platinumaward
import graduateplusbcu.composeapp.generated.resources.silveraward
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onActivityClick: (String, DrawableResource) -> Unit,
    onEventsClick: () -> Unit
) {
    if (uiState.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(4293519853))
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .background(Color(4293519853))
                .padding(paddingValues)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(
                        top = ExtraLargeSpacing + LargeSpacing,
                        start = LargeSpacing + MediumSpacing,
                        end = LargeSpacing + MediumSpacing,
                        bottom = LargeSpacing
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(LargeSpacing)
            ) {
                Image(
                    modifier = modifier.height(100.dp).width(400.dp),
                    painter = painterResource(resource = Res.drawable.gplusbanner),
                    contentDescription = null
                )

                Text(
                    text = "Graduate+ is an extra & co-curricular awards framework offered to all students at BCU across all faculties & courses. It is designed to help you stand out from the crowd, whilst preparing you for your future.\n" +
                            "\n" +
                            "At BCU, we do things differently. When you immerse yourself in everything we have to offer, you’ll be building on your current skillset, whilst having some fun too.\n" +
                            "\n" +
                            "You’ll have the chance to consider new ideas, network with others, attend events, work on projects – all of which will help you to grow both professionally and personally for your future, and beyond. ",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = modifier.height(LargeSpacing))
            }

            ActivitySection(
                modifier = modifier.padding(top = MediumSpacing),
                activityText = "Graduate+ Week Events",
                awardAchieved = 0,
                onActivityClick = {
                    onEventsClick()
                },
                icon = Res.drawable.events
            )

            uiState.profile?.let {
                ActivitySection(
                    modifier = modifier,
                    activityText = "Bronze Award: Activities",
                    awardAchieved = it.bronzeAchieved,
                    onActivityClick = {
                        onActivityClick("BronzeActivities", Res.drawable.bronzeaward)
                    },
                    icon = Res.drawable.bronzeaward
                )

                ActivitySection(
                    modifier = modifier,
                    activityText = "Silver Award: Activities",
                    awardAchieved = it.silverAchieved,
                    onActivityClick = {
                        onActivityClick("SilverActivities", Res.drawable.silveraward)
                    },
                    icon = Res.drawable.silveraward
                )

                ActivitySection(
                    modifier = modifier,
                    activityText = "Gold Award: Activities",
                    awardAchieved = it.goldAchieved,
                    onActivityClick = {
                        onActivityClick("GoldActivities", Res.drawable.goldaward)
                    },
                    icon = Res.drawable.goldaward
                )

                ActivitySection(
                    modifier = modifier,
                    activityText = "Platinum Award: Activities",
                    awardAchieved = it.platinumAchieved,
                    onActivityClick = {
                        onActivityClick("PlatinumActivities", Res.drawable.platinumaward)
                    },
                    icon = Res.drawable.platinumaward
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActivitySection(
    modifier: Modifier,
    activityText: String,
    awardAchieved: Int,
    onActivityClick: () -> Unit,
    icon: DrawableResource
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = MediumSpacing,
                bottom = MediumSpacing
            ),
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = activityText, style = MaterialTheme.typography.titleSmall)
                    Button(
                        onClick = onActivityClick,
                        modifier = modifier
                            .height(40.dp)
                            .width(200.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(4282061423),
                            contentColor = Color(-1)
                        )
                    ) {
                        Text(
                            text = "Select"
                        )
                    }
                }

                if (awardAchieved == 1) {
                    IconButton(
                        onClick = {},
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

                Image(
                    painter = painterResource(resource = icon),
                    contentDescription = null,
                    modifier = modifier.size(52.dp)
                )
            }
        }
    }
}