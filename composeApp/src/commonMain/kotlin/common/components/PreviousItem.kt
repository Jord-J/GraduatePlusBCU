package common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.theming.SmallElevation

@Composable
fun PreviousItem(
    modifier: Modifier = Modifier,
    previous: Previous,
    onPreviousClick: (Previous) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(4293519853))
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(0.9f)
                    .shadow(
                        elevation = SmallElevation
                    )
                    .background(color = MaterialTheme.colorScheme.surface)
                    .clickable { onPreviousClick(previous) },
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = Color(4282061423)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier.padding(start = 5.dp),
                        text = "Request ID: ${previous.id}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
                Text(
                    modifier = modifier.padding(start = 5.dp),
                    text = "Activity: ${previous.title}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    modifier = modifier.padding(start = 5.dp),
                    text = "User name: ${previous.name}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    modifier = modifier.padding(start = 5.dp),
                    text = "User email: ${previous.email}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}