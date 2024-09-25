package screens.admin

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.theming.ButtonHeight
import common.theming.LargeSpacing
import common.theming.MediumElevation
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.bculogo
import graduateplusbcu.composeapp.generated.resources.logout_button
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AdminScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: AdminUiState,
    onActiveClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(Color(4293519853))
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Request(
            title = "Active Requests",
            clickHere = "Click here to action active requests",
            description = "There are currently: ${uiState.activeRequests} active request(s)",
            onClick = onActiveClick
        )

        Request(
            title = "Previous Requests",
            clickHere = "Click here to view previous requests",
            description = "There are currently: ${uiState.previousRequests} previous request(s)",
            onClick = onPreviousClick
        )

        Button(
            onClick = {
                onSignOutClick()
            },
            modifier = modifier
                .padding(top = 20.dp)
                .fillMaxWidth(0.9f)
                .height(ButtonHeight),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 0.dp
            ),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(4282061423),
                contentColor = Color(-1)
            )
        ) {
            Text(text = stringResource(resource = Res.string.logout_button))
        }
    }
}

@Composable
@OptIn(ExperimentalResourceApi::class)
fun Request(
    title: String,
    clickHere: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = LargeSpacing
            ),
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .shadow(elevation = MediumElevation)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(all = 5.dp)
                .clickable { onClick() },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge
                )

                Image(
                    painter = painterResource(Res.drawable.bculogo),
                    contentDescription = null
                )
            }

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                drawRect(
                    color = Color.LightGray,
                    size = Size(size.width, 3f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = clickHere,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }
        }
    }
}