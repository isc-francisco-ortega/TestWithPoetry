package com.example.testwithpoetry.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.testwithpoetry.R
import com.example.testwithpoetry.ui.viewmodels.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel
){
    val user by profileViewModel.user
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Profile")
                    }
                }
            )
        },
    ){innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar_photo), // Aquí debes poner tu imagen (reemplaza con tu imagen)
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(150.dp) // Tamaño de la imagen (ajústalo como desees)
                    .clip(CircleShape) // Forma circular
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = stringResource(R.string.name_text_field_label))
            user?.let {
                Text(
                    it.name,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = stringResource(R.string.email_text_field_label))
            user?.let {
                Text(
                    it.email,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = stringResource(R.string.birthday_text_field_label))
            user?.let {
                Text(
                    text = dateFormat(it.birthday),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }

}

@Composable
fun dateFormat(birthdayLong: Long): String {
    val date = Date(birthdayLong)
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = sdf.format(date)
    return formattedDate
}