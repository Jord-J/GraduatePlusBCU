package screens.account.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.components.CircleImage
import common.components.EditButton
import common.theming.ButtonHeight
import common.theming.LargeSpacing
import common.theming.MediumSpacing
import common.theming.SmallSpacing
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.bronzeaward
import graduateplusbcu.composeapp.generated.resources.goldaward
import graduateplusbcu.composeapp.generated.resources.logout_button
import graduateplusbcu.composeapp.generated.resources.platinumaward
import graduateplusbcu.composeapp.generated.resources.silveraward
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AccountScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: AccountUiState,
    fetchData: () -> Unit,
    onButtonClick: () -> Unit,
    onSignOut: () -> Unit,
    signOut: () -> Unit,
    goToAward: (String, DrawableResource) -> Unit
) {
    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize().padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(4293519853))
                .verticalScroll(state = rememberScrollState())
        ) {
            CreateProfile(
                name = uiState.profile?.name ?: "",
                bio = uiState.profile?.bio ?: "",
                imageUrl = uiState.profile?.imageUrl ?: "",
                onButtonClick = onButtonClick,
                onSignOutClick = onSignOut,
                uiState = uiState,
                badgeCount = uiState.profile?.badgeCount ?: 0,
                eventCount = uiState.profile?.eventCount ?: 0,
                goToAward = goToAward
            )
        }
    }

    LaunchedEffect(
        key1 = Unit,
        key2 = uiState.signedOut,
        block = {
            if (uiState.signedOut) {
                signOut()
            }
            fetchData()
        }
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateProfile(
    modifier: Modifier = Modifier,
    name: String,
    bio: String,
    badgeCount: Int,
    eventCount: Int,
    imageUrl: String,
    onButtonClick: () -> Unit,
    onSignOutClick: () -> Unit,
    uiState: AccountUiState,
    goToAward: (String, DrawableResource) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MediumSpacing)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(all = LargeSpacing)
    ) {
        CircleImage(modifier = modifier.size(90.dp), url = imageUrl, onClick = {})

        Spacer(modifier = modifier.height(SmallSpacing))

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(text = bio, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = modifier.height(MediumSpacing))

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier.weight(1f)
            ) {
                CountText(count = badgeCount, text = "badges")
                CountText(count = eventCount, text = "events")
            }

            EditButton(
                text = "Edit",
                onClick = onButtonClick,
                modifier = modifier
                    .heightIn(38.dp)
                    .widthIn(100.dp)
            )
        }

        if (uiState.profile?.bio?.isEmpty() == true || uiState.profile?.imageUrl?.isEmpty() == true) {
            Spacer(modifier = modifier.height(LargeSpacing))

            Text(text = "Complete following sections.")

            var count = 1

            if (uiState.profile.imageUrl.isEmpty()) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SmallSpacing)
                ) {
                    Box(
                        modifier = modifier.padding(SmallSpacing),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = modifier.width(40.dp).height(40.dp),
                            progress = {
                                0.10f
                            },
                            color = Color(red = 59, green = 144, blue = 18),
                            trackColor = Color(red = 190, green = 190, blue = 197),
                        )
                        Text(count.toString(), fontWeight = FontWeight.Bold)
                    }
                    Text(text = "Add a profile picture")
                }
                count++
            }

            if (uiState.profile.bio.isEmpty()) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SmallSpacing)
                ) {
                    Box(
                        modifier = modifier.padding(SmallSpacing),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = modifier.width(40.dp).height(40.dp),
                            progress = {
                                0.10f
                            },
                            color = Color(red = 59, green = 144, blue = 18),
                            trackColor = Color(red = 190, green = 190, blue = 197),
                        )
                        Text(count.toString(), fontWeight = FontWeight.Bold)
                    }
                    Text(text = "Add a bio")
                }
                count++
            }
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = MediumSpacing)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(all = LargeSpacing)
        ) {
            Text(text = "Badges", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = modifier.height(MediumSpacing))
            Text(
                text = "These badges are given only to those who complete the related activities",
                style = MaterialTheme.typography.bodyMedium
            )

            if (uiState.profile?.badgeCount == 0) {
                Spacer(modifier = modifier.height(LargeSpacing))
                Text(
                    text = "You do not have any badges!",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.54f),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                )
            } else {
                if (uiState.badges?.bronzeAchieved == 1) {
                    Badge(modifier = modifier.clickable {
                        goToAward(
                            "bronze",
                            Res.drawable.bronzeaward
                        )
                    }, "Bronze", Res.drawable.bronzeaward)
                }
                if (uiState.badges?.silverAchieved == 1) {
                    Badge(modifier = modifier.clickable {
                        goToAward(
                            "silver",
                            Res.drawable.silveraward
                        )
                    }, "Silver", Res.drawable.silveraward)
                }
                if (uiState.badges?.goldAchieved == 1) {
                    Badge(modifier = modifier.clickable {
                        goToAward(
                            "gold",
                            Res.drawable.goldaward
                        )
                    }, "Gold", Res.drawable.goldaward)
                }
                if (uiState.badges?.platinumAchieved == 1) {
                    Badge(modifier = modifier.clickable {
                        goToAward("platinum", Res.drawable.platinumaward)
                    }, "Platinum", Res.drawable.platinumaward)
                }
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = MediumSpacing)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(all = LargeSpacing)
        ) {
            Button(
                onClick = {
                    onSignOutClick()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 0.dp
                ),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(4282061423),
                    contentColor = Color(-1)
                )
            ) {
                Text(text = stringResource(resource = Res.string.logout_button))
            }
        }
    }
}

@Composable
fun CountText(
    count: Int,
    text: String
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )
            ) {
                append(text = "$count  ")
            }

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.54f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            ) {
                append(text = "$text  ")
            }
        }
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Badge(
    modifier: Modifier = Modifier,
    badge: String,
    image: DrawableResource
) {
    Row(
        modifier = modifier
            .padding(all = 10.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, SolidColor(Color.Gray))),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier.size(40.dp),
                painter = painterResource(image),
                contentDescription = null
            )
            Text(text = "$badge Award - BCU Graduate+", style = MaterialTheme.typography.bodyMedium)
        }
    }
}