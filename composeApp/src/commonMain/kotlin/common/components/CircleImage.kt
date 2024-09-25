package common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.profile_placeholder
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CircleImage(
    modifier: Modifier,
    url: String,
    onClick: () -> Unit
) {
    KamelImage(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() },
        resource = asyncPainterResource(url),
        contentDescription = null,
        onLoading = { CircularProgressIndicator() },
        onFailure = {
            Image(
                painterResource(resource = Res.drawable.profile_placeholder),
                contentDescription = null
            )
        },
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}