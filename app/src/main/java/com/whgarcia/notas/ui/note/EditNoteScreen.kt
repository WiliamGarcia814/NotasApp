package com.whgarcia.notas.ui.note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.whgarcia.notas.R
import com.whgarcia.notas.model.Notas
import com.whgarcia.notas.state.NotasState
import com.whgarcia.notas.ui.components.ContentTextField
import com.whgarcia.notas.ui.components.FloatingButton
import com.whgarcia.notas.ui.components.MainTextField
import com.whgarcia.notas.ui.components.dateAndTimeEditNow
import com.whgarcia.notas.ui.components.dateAndTimeNow
import com.whgarcia.notas.viewmodel.NotasViewModel
import com.whgarcia.notas.viewmodel.StateNotasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavHostController,
    stateNoteVM: StateNotasViewModel,
    id: Long,
    notasVM: NotasViewModel
) {
    var expanded by remember { mutableStateOf(false) } // Estado para el menú desplegable
    // Cargar la nota cuando se lance el Composable
    val state = stateNoteVM.state

    // Cargar la nota cuando se lance el Composable
    LaunchedEffect(Unit) {
        stateNoteVM.getNoteById(id)
    }

    // Manejar el botón "Atrás" del sistema
    BackHandler {
        if (state.edit){
            // Si está en modo de edición, cambiar al modo de vista previa
            stateNoteVM.toggleEditMode() // Cambia el estado de edición
        }else{
            // Si no está en modo de edición, salir de la pantalla normalmente
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.edit){
                        MainTextField(value = state.title, onValueChange = { stateNoteVM.onValue(it, "title") })
                    }else{
                        Text(
                            text = state.title
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    if (!state.edit){
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                        }
                    }
                },
                actions = {
                    // Botón de Editar
                    if (!state.edit){
                        IconButton(onClick = {stateNoteVM.toggleEditMode()}) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                        }
                        // Botón para mostrar más opciones
                        IconButton(onClick = { expanded = true }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Más opciones")
                        }
                        // Menú desplegable con opciones
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Eliminar") },
                                onClick = {
                                    expanded = false
                                    // Acción cuando se presiona eliminar
                                    notasVM.updateNote(
                                        Notas(
                                            id = id,
                                            title = state.title,
                                            content = state.content,
                                            create_date = state.create_date,
                                            edit_date = state.edit_date,
                                            delete = true
                                        )
                                    )
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (state.edit){
                FloatingButton(
                    onClick = {
                        // Actualiza la nota en ViewModel
                        notasVM.updateNote(
                            Notas(
                                id = id,
                                title = state.title,
                                content = state.content,
                                create_date = state.create_date,
                                edit_date = dateAndTimeEditNow()
                            )
                        )
                        // Navega hacia atrás después de actualizar la nota
                        navController.popBackStack()
                    },
                    icon = painterResource(id = R.drawable.ic_save_24),
                    desc = stringResource(id = R.string.cd_save_note)
                )
            }
        }
    ){ innerPadding ->
        EditContent(
            modifier = Modifier
                .padding(innerPadding),
            state,
            stateNoteVM = stateNoteVM
        )
    }
}

@Composable
fun EditContent(
    modifier: Modifier = Modifier,
    state: NotasState,
    stateNoteVM: StateNotasViewModel
) {
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
            Text(text = if(state.edit) "Editando" else state.edit_date)
            Text(text = state.create_date)
        }
        if (state.edit){
            ContentTextField(
                value = state.content,
                onValueChange = {stateNoteVM.onValue(it, "content")}
            )
        }else{
            Text(text = state.content)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            stateNoteVM.limpiar()
        }
    }
}