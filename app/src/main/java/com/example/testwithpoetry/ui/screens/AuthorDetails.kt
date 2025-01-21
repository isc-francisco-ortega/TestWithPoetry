package com.example.testwithpoetry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.testwithpoetry.ui.viewmodels.AuthorDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorDetails(
    authorName: String,
    authorDetailsViewModel: AuthorDetailsViewModel
){
    authorDetailsViewModel.getAuthorDetails(authorName)
    val authorDetails by authorDetailsViewModel.authorDetails.collectAsState()
    val isLoading by authorDetailsViewModel.loading.collectAsState()
    val poemDetails by authorDetailsViewModel.poemDetails.collectAsState()
    val showDialog by authorDetailsViewModel.isDialogOpen

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = authorName)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(authorDetails) { title ->
                    PoemItem(title.title,
                        onClick = {
                            // Obtener detalles del poema al hacer clic
                            authorDetailsViewModel.getPoem(authorName, title.title)
                        }
                    )
                }
            }

        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
    if (showDialog && !isLoading && poemDetails != null) {
        poemDetails?.let {
            PoemDialog(
                title = it.title,
                poem = authorDetailsViewModel.getPoemString(it.lines),
                onDismiss = {
                    authorDetailsViewModel.closeDialog()
                    println("showDialog: $showDialog")
                }
            )
        }
    }

}

@Composable
fun PoemItem(
    title: String,
    onClick: ()-> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onClick()
            }
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PoemDialog(
    title: String,
    poem: String,
    onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.surface,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(text = title, style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = poem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Button(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        onClick = onDismiss) {
                        Text("Close")
                    }
                }

            }
        }
    }
}
