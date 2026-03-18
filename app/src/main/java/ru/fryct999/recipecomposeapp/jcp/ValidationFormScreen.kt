package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class ValidationFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeComposeAppTheme {
                ValidationFormExample()
            }
        }
    }
}

@Composable
fun ValidationFormExample() {
    var nameState by remember { mutableStateOf("") }
    var nameErrorState by remember { mutableStateOf(false) }
    var nameSupportTextState by remember { mutableStateOf("") }

    var passState by remember { mutableStateOf("") }
    var passErrorState by remember { mutableStateOf(false) }
    var passSupportTextState by remember { mutableStateOf("") }

    var mailState by remember { mutableStateOf("") }
    var mailErrorState by remember { mutableStateOf(false) }
    var mailSupportTextState by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isButtonEnable =
        remember(nameState, passState, mailState, nameErrorState, passErrorState, mailErrorState) {
            nameState.isNotBlank() && passState.isNotBlank() && mailState.isNotBlank() && !nameErrorState && !passErrorState && !mailErrorState
        }

    val regexNum = "\\d+".toRegex()
    val regexSpecChar = "[^A-Za-z0-9]".toRegex()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            OutlinedTextField(
                value = nameState,
                label = { Text("Имя пользователя") },
                singleLine = true,
                onValueChange = {
                    nameState = it
                    nameErrorState = nameState.length < 2 && nameState.isNotEmpty()
                    nameSupportTextState = if (nameErrorState) "Некорректный ник" else ""
                },
                modifier = Modifier.fillMaxWidth(),
                isError = nameErrorState,
                supportingText = {
                    Text(text = nameSupportTextState)
                }
            )

            OutlinedTextField(
                value = passState,
                label = { Text("Пароль") },
                singleLine = true,
                onValueChange = {
                    passState = it
                    passErrorState =
                        (passState.length < 8 || !regexNum.containsMatchIn(passState) || !regexSpecChar.containsMatchIn(
                            passState
                        )) && passState.isNotEmpty()
                    passSupportTextState = if (passErrorState) "Некорректный пароль" else ""
                },
                modifier = Modifier.fillMaxWidth(),
                isError = passErrorState,
                supportingText = {
                    Text(text = passSupportTextState)
                }
            )

            OutlinedTextField(
                value = mailState,
                label = { Text("Email адрес") },
                singleLine = true,
                onValueChange = {
                    mailState = it
                    mailErrorState = !EMAIL_ADDRESS.matcher(it).matches() && mailState.isNotEmpty()
                    mailSupportTextState = if (mailErrorState) "Некорректный email" else ""
                },
                modifier = Modifier.fillMaxWidth(),
                isError = mailErrorState,
                supportingText = {
                    Text(text = mailSupportTextState)
                }
            )

            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Данные отправлены")
                    }

                    nameState = ""
                    nameErrorState = false
                    nameSupportTextState = ""
                    passState = ""
                    passErrorState = false
                    passSupportTextState = ""
                    mailState = ""
                    mailErrorState = false
                    mailSupportTextState = ""
                },
                enabled = isButtonEnable
            ) {
                Text(text = "Отправить")
            }
        }
    }
}

@Preview
@Composable
fun ValidationFormPreview() {
    RecipeComposeAppTheme {
        ValidationFormExample()
    }
}