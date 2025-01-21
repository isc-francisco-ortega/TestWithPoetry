package com.example.testwithpoetry.ui.screens

import android.app.DatePickerDialog
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testwithpoetry.R
import com.example.testwithpoetry.ui.viewmodels.UserFormViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun UserFormScreen(
    navigateToAuthorsList:()-> Unit,
    viewModel: UserFormViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val user = viewModel.user
    val nameError = remember { mutableStateOf<String?>(null) }
    val emailError = remember { mutableStateOf<String?>(null) }
    val birthdayError = remember { mutableStateOf<String?>(null) }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            viewModel.onBirthdayChanged(calendar.timeInMillis) // Actualizar la fecha
            birthdayError.value = null // Limpiar el error si seleccionan una fecha válida
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.user_form_greeting))
            OutlinedTextField(
                value = user.value.name,
                onValueChange = {
                    viewModel.onNameChanged(it)
                    nameError.value = null // Limpiar error al escribir
                },
                label = { Text(text = stringResource(R.string.name_text_field_label)) },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError.value != null,
                supportingText = {
                    if (nameError.value != null) {
                        Text(text = nameError.value!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            OutlinedTextField(
                value = user.value.email,
                onValueChange = {
                    viewModel.onEmailChanged(it)
                    emailError.value = null // Limpiar error al escribir
                },
                label = { Text(text = stringResource(R.string.email_text_field_label)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError.value != null,
                supportingText = {
                    if (emailError.value != null) {
                        Text(text = emailError.value!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            OutlinedTextField(
                value = if (user.value.birthday > 0L) {
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    sdf.format(Date(user.value.birthday))
                } else {
                    ""
                },
                onValueChange = { },
                label = { Text(text = stringResource(R.string.birthday_text_field_label)) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                    }
                },
                isError = birthdayError.value != null,
                supportingText = {
                    if (birthdayError.value != null) {
                        Text(text = birthdayError.value!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }

        Button(
            onClick = {
                var isValid = true

                if (user.value.name.isBlank()) {
                    nameError.value = "Name can't be empty"
                    isValid = false
                }

                if (user.value.email.isBlank() || !isValidEmail(user.value.email)) {
                    emailError.value = "Invalid email"
                    isValid = false
                }

                if (user.value.birthday == 0L) {
                    birthdayError.value = "Select your birthday"
                    isValid = false
                }

                if (isValid) {
                    println("Nombre: ${user.value.name}, Correo: ${user.value.email}, Cumpleaños: ${user.value.birthday}")
                    viewModel.saveUser()
                    navigateToAuthorsList()

                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Open Authors List")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        UserFormScreen(
            {},
            viewModel = hiltViewModel()
        )
    }
}

private fun isValidEmail(email: String): Boolean {
    val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return EMAIL_REGEX.matches(email)
}