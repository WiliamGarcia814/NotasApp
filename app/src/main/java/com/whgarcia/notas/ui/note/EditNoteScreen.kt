package com.whgarcia.notas.ui.note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.whgarcia.notas.ui.components.ColorPickerDialog
import com.whgarcia.notas.ui.components.ContentTextField
import com.whgarcia.notas.ui.components.FloatingButton
import com.whgarcia.notas.ui.components.MainTextField
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
    // Cargar la nota cuando se lance el Composable
    val state = stateNoteVM.state
    var expanded by remember { mutableStateOf(false) } // Estado para el menú desplegable
    var selectedColor by remember { mutableStateOf(Color(state.color)) } // Color seleccionado por defecto
    var isColorPickerVisible by remember { mutableStateOf(false) }  // Estado para controlar la visibilidad del diálogo

    // Cargar la nota cuando se lance el Composable
    LaunchedEffect(Unit) {
        stateNoteVM.getNoteById(id)
    }

    // Observar cambios en el estado y actualizar el color
    LaunchedEffect(state) {
        selectedColor = Color(state.color) // Actualizar el color seleccionado cuando el estado cambia
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
                        onClick = {
                            if (state.edit){
                                // Si está en modo de edición, cambiar al modo de vista previa
                                stateNoteVM.toggleEditMode() // Cambia el estado de edición
                            }else{
                                // Si no está en modo de edición, salir de la pantalla normalmente
                                navController.popBackStack()
                            }
                        },
                        icon = painterResource(id = R.drawable.ic_arrow_back_24),
                        desc = stringResource(id = R.string.cd_back),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    // Vista prevía
                    if (!state.edit){
                        // Botón de Editar
                        BoxWithIconButton(
                            onClick = { stateNoteVM.toggleEditMode() },
                            icon = painterResource(id = R.drawable.ic_edit_24),
                            desc = stringResource(id = R.string.cd_edit_note),
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        // Botón para mostrar más opciones
                        BoxWithIconButton(
                            onClick = { expanded = true},
                            icon = painterResource(id = R.drawable.ic_more_vert_24),
                            desc = stringResource(id = R.string.cd_more_menu),
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        // Menú desplegable con opciones
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_delete_24),
                                            contentDescription = stringResource(id = R.string.cd_delete_note),
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                        Text(stringResource(id = R.string.cd_delete_note))
                                    }
                                },
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
                                            delete = true,
                                            color_note = state.color
                                        )
                                    )
                                    navController.popBackStack()
                                }
                            )
                        }
                    }else{
                        // Botón para mostrar el menú de colores
                        BoxWithIconButton(
                            onClick = { isColorPickerVisible = true },
                            icon = painterResource(id = R.drawable.ic_palette_24),
                            desc = stringResource(id = R.string.cd_color_note),
                            modifier = Modifier.padding(end = 16.dp),
                            contentColor = selectedColor
                        )
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
                                edit_date = dateAndTimeNow(),
                                color_note = selectedColor.toArgb()
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
        // Mostrar el diálogo de selección de color cuando `isColorPickerVisible` sea true
        if (isColorPickerVisible) {
            ColorPickerDialog(
                selectedColor = selectedColor,
                onColorSelected = { color ->
                    selectedColor = color  // Actualizar el color seleccionado
                    isColorPickerVisible = false  // Cerrar el diálogo al seleccionar un color
                },
                onDismiss = {
                    isColorPickerVisible = false  // Cerrar el diálogo si se cancela
                }
            )
        }
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
            .padding(16.dp)
    ) {
        MainTextField(
            value = state.title,
            onValueChange = {stateNoteVM.onValue(it, "title")},
            enabled = state.edit
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
            enabled = state.edit
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            stateNoteVM.limpiar()
        }
    }
}