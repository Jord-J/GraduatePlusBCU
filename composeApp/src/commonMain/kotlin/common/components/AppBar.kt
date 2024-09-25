package common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.arrow_back
import graduateplusbcu.composeapp.generated.resources.person_circle_icon
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.account.profile.Account

@Suppress("UNUSED_EXPRESSION")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CustomAppBar(
    title: String,
    navigator: Navigator,
    screen: Screen
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(4282061423),
            titleContentColor = Color(4294967295)
        ),
        title = {
            Text(text = title)
        },
        actions = {
            AnimatedVisibility(visible = title == "Home") {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color(4294967295)),
                    onClick = {
                        navigator.push(Account())
                    }
                ) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.person_circle_icon),
                        contentDescription = null
                    )
                }
            }
        },
        navigationIcon = {
            if (showNavigationIcon(title)) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color(4294967295)),
                    onClick = { navigator.push(screen) }
                ) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.arrow_back),
                        contentDescription = null
                    )
                }
            } else {
                null
            }
        }
    )
}

private fun showNavigationIcon(currentDestinationRoute: String?): Boolean {
    return !(currentDestinationRoute == "Login"
            || currentDestinationRoute == "Home"
            || currentDestinationRoute == "Admin"
            || currentDestinationRoute == null
            )
}