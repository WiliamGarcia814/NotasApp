package com.whgarcia.notas.ui.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whgarcia.notas.R
import com.whgarcia.notas.ui.components.BoxWithIconButton
import com.whgarcia.notas.ui.components.ListCard
import com.whgarcia.notas.viewmodel.NotasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteNoteScreen(navController: NavHostController, notasVM: NotasViewModel) {
    LaunchedEffect(Unit) {
        notasVM.getAllDeletedNotes()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.view_trash),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    BoxWithIconButton(
                        onClick = { navController.popBackStack() },
                        icon = painterResource(id = R.drawable.ic_arrow_back_24),
                        desc = stringResource(id = R.string.cd_back),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        DeleteNoteContent(
            modifier = Modifier.padding(innerPadding),
            notasVM,
            navController
        )
    }
}

@Composable
fun DeleteNoteContent(
    modifier: Modifier,
    notasVM: NotasViewModel,
    navController: NavHostController
){
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // Observar el StateFlow de las notas eliminadas
        val deletedNotes by notasVM.deletedNotes.collectAsState()
        // Mostrar en modo lista
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(deletedNotes){ item ->
                ListCard(item = item) {
                    navController.navigate("recoverNote/${item.id}")
                }
            }
        }
    }
}