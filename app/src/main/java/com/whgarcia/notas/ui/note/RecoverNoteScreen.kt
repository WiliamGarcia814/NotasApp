package com.whgarcia.notas.ui.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whgarcia.notas.R
import com.whgarcia.notas.model.Notas
import com.whgarcia.notas.state.NotasState
import com.whgarcia.notas.ui.components.BoxWithIconButton
import com.whgarcia.notas.ui.components.ContentTextField
import com.whgarcia.notas.ui.components.MainTextField
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
                        text = state.title,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    BoxWithIconButton(
                        onClick = { navController.popBackStack() },
                        icon = painterResource(id = R.drawable.ic_arrow_back_24),
                        desc = stringResource(id = R.string.cd_back),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    // Boton para recuperar la nota
                    BoxWithIconButton(
                        onClick = {
                            stateNoteVM.showRecoverConfirmation()
                        },
                        icon = painterResource(id = R.drawable.ic_replay_24),
                        desc = stringResource(id = R.string.cd_recover_note),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    // Boton para eliminar la nota
                    BoxWithIconButton(
                        onClick = {
                            stateNoteVM.showDeleteConfirmation()
                        },
                        icon = painterResource(id = R.drawable.ic_delete_forever_24),
                        desc = stringResource(id = R.string.cd_delete_forever_note),
                        modifier = Modifier.padding(end = 16.dp)
                    )
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

        // Diálogo de confirmación de eliminación
        if (state.showDeleteConfirmation){
            AlertDialog(
                onDismissRequest = { stateNoteVM.showDeleteConfirmation() },
                title = { Text(text = stringResource(id = R.string.cd_delete_forever_note)) },
                text = { Text(text = stringResource(id = R.string.dialog_delete_forever_message)) },
                confirmButton = {
                    TextButton(onClick = {
                        stateNoteVM.showDeleteConfirmation()
                        notasVM.deleteNote(
                            Notas(id = id,
                                title = state.title,
                                content = state.content,
                                create_date = state.create_date,
                                edit_date = state.edit_date,
                                color_note = state.selectedColor.toArgb()
                            )
                        )
                        navController.popBackStack()
                    }) {
                        Text(text = stringResource(id = R.string.dialog_confirm))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { stateNoteVM.showDeleteConfirmation() }) {
                        Text(text = stringResource(id = R.string.txt_close))
                    }
                }
            )
        }
        // Diálogo de confirmación de recuperación
        if (state.showRecoverConfirmation){
            AlertDialog(
                onDismissRequest = { stateNoteVM.showRecoverConfirmation() },
                title = { Text(text = stringResource(id = R.string.cd_recover_note)) },
                text = { Text(text = stringResource(id = R.string.dialog_recover_message)) },
                confirmButton = {
                    TextButton(onClick = {
                        stateNoteVM.showRecoverConfirmation()
                        notasVM.updateNote(
                            Notas(
                                id = id,
                                title = state.title,
                                content = state.content,
                                create_date = state.create_date,
                                edit_date = state.edit_date,
                                delete = false,
                                color_note = state.selectedColor.toArgb()
                            )
                        )
                        navController.popBackStack()
                    }) {
                        Text(text = stringResource(id = R.string.dialog_confirm))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { stateNoteVM.showRecoverConfirmation() }) {
                        Text(text = stringResource(id = R.string.txt_close))
                    }
                }
            )
        }
    }
}

@Composable
fun RecoverNoteContent(
    modifier: Modifier,
    state: NotasState,
    stateNoteVM: StateNotasViewModel
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MainTextField(
            value = state.title,
            onValueChange = {stateNoteVM.onValue(it, "title")},
            enabled = false
        )
        Text(
            text = state.create_date,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        ContentTextField(
            value = state.content,
            onValueChange = {stateNoteVM.onValue(it, "content")},
            enabled = false
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            stateNoteVM.limpiar()
        }
    }
}