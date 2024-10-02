package com.whgarcia.notas.ui.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.whgarcia.notas.R
import com.whgarcia.notas.model.Notas
import com.whgarcia.notas.state.NotasState
import com.whgarcia.notas.viewmodel.NotasViewModel
import com.whgarcia.notas.viewmodel.StateNotasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverNoteScreen(
    navController: NavHostController,
    stateNoteVM: StateNotasViewModel,
    id: Long,
    notasVM: NotasViewModel
) {
    // Cargar la nota cuando se lance el Composable
    val state = stateNoteVM.state

    // Cargar la nota cuando se lance el Composable
    LaunchedEffect(Unit) {
        stateNoteVM.getNoteById(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.title
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    // Boton para recuperar la nota
                    IconButton(onClick = { 
                        notasVM.updateNote(
                            Notas(
                                id = id,
                                title = state.title,
                                content = state.content,
                                create_date = state.create_date,
                                edit_date = state.edit_date,
                                delete = false
                            )
                        )
                        navController.popBackStack()
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_replay_24), contentDescription = "Recuperar")
                    }
                    // Boton para eliminar la nota
                    IconButton(onClick = { 
                        notasVM.deleteNote(
                            Notas(id = id,
                                title = state.title,
                                content = state.content,
                                create_date = state.create_date,
                                edit_date = state.edit_date
                            )
                        )
                        navController.popBackStack()
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_delete_forever_24), contentDescription = "Eliminar definitvamente")
                    }
                }
            )
        }
    ){ innerPadding ->
        RecoverNoteContent(
            modifier = Modifier
                .padding(innerPadding),
            state,
            stateNoteVM = stateNoteVM
        )
    }
}

@Composable
fun RecoverNoteContent(modifier: Modifier, state: NotasState, stateNoteVM: StateNotasViewModel){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Papelera")
            Text(text = state.create_date)
        }
        
        Text(text = state.content)
    }

    DisposableEffect(Unit) {
        onDispose {
            stateNoteVM.limpiar()
        }
    }
}