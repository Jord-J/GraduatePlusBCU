package screens.account.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import common.components.CircleImage
import common.components.CustomTextField
import common.theming.ButtonHeight
import common.theming.ExtraLargeSpacing
import common.theming.LargeSpacing
import common.theming.SmallElevation
import graduateplusbcu.composeapp.generated.resources.Res
import graduateplusbcu.composeapp.generated.resources.bio_hint
import graduateplusbcu.composeapp.generated.resources.confirm_button
import graduateplusbcu.composeapp.generated.resources.name_hint
import graduateplusbcu.composeapp.generated.resources.password_hint
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EditScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: EditUiState,
    bioTextFieldValue: TextFieldValue,
    onBioChange: (TextFieldValue) -> Unit,
    onUploadButtonClick: () -> Unit,
    onUploadSucceed: () -> Unit,
    fetchData: () -> Unit,
    onNameChange: (String) -> Unit,
    onProfilePictureChange: (ImageBitmap) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    var image by remember {
        mutableStateOf(ImageBitmap(width = 1, height = 1))
    }

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { it ->
            it.forEach {
                // Process the selected images' ByteArrays.
                onProfilePictureChange(it.toImageBitmap())
                image = it.toImageBitmap()
            }
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.profile != null -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.surface)
                        .padding(ExtraLargeSpacing),
                    verticalArrangement = Arrangement.spacedBy(LargeSpacing),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        CircleImage(
                            url = "test",
                            modifier = modifier
                                .clip(CircleShape)
                                .size(150.dp),
                            onClick = {
                                singleImagePicker.launch()
                            }
                        )

                        IconButton(
                            onClick = {
                            },
                            modifier = modifier
                                .align(Alignment.BottomEnd)
                                .shadow(
                                    elevation = SmallElevation,
                                    shape = RoundedCornerShape(percent = 25)
                                )
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(percent = 25)
                                )
                                .size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = modifier.height(LargeSpacing))

                    Text(text = "Name", style = MaterialTheme.typography.labelMedium)

                    CustomTextField(
                        value = uiState.profile.name,
                        onValueChange = onNameChange,
                        hint = Res.string.name_hint,
                        keyboardType = KeyboardType.Ascii
                    )

                    Text(text = "Biography", style = MaterialTheme.typography.labelMedium)

                    BioTextField(value = bioTextFieldValue, onValueChange = onBioChange)

                    Text(text = "Password", style = MaterialTheme.typography.labelMedium)

                    CustomTextField(
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        hint = Res.string.password_hint,
                        keyboardType = KeyboardType.Password,
                        isPasswordTextField = true
                    )

                    Button(
                        onClick = { onUploadButtonClick() },
                        modifier = modifier
                            .fillMaxWidth()
                            .height(ButtonHeight),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(4282061423),
                            contentColor = Color(-1)
                        )
                    ) {
                        Text(text = stringResource(resource = Res.string.confirm_button))
                    }

                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = { fetchData() })

    LaunchedEffect(
        key1 = uiState.uploadSucceed,
        block = {
            if (uiState.uploadSucceed) {
                onUploadSucceed()
            }
        }
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BioTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    val maxChar = 110
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.text.length < maxChar) {
                onValueChange(
                    TextFieldValue(
                        text = it.text,
                        selection = TextRange(it.text.length)
                    )
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Ascii
        ),
        placeholder = {
            Text(
                text = stringResource(resource = Res.string.bio_hint),
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        },
        shape = RoundedCornerShape(4.dp)
    )
    Text(
        text = "Max. character count: 110",
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.Center
    )
}