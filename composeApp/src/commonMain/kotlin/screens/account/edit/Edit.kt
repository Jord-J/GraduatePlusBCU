package screens.account.edit

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.components.CustomAppBar
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.edit_page
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.account.profile.Account

class Edit : Screen {
    private val viewModel = EditViewModel()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { CustomAppBar(stringResource(Res.string.edit_page), navigator, Account()) },
            content = {
                EditScreen(
                    paddingValues = it,
                    uiState = viewModel.uiState,
                    fetchData = { viewModel.fetchData() },
                    bioTextFieldValue = viewModel.bioTextFieldValue,
                    onBioChange = viewModel::onBioChange,
                    onUploadButtonClick = { viewModel.upload() },
                    onUploadSucceed = { navigator.pop() },
                    onNameChange = viewModel::onNameChange,
                    onProfilePictureChange = viewModel::onProfilePictureChange,
                    onPasswordChange = viewModel::updatePassword
                )
            }
        )
    }
}