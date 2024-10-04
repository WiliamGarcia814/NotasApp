package com.whgarcia.notas.ui.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whgarcia.notas.R
import com.whgarcia.notas.model.Notas
import com.whgarcia.notas.state.NotasState
import com.whgarcia.notas.ui.components.BoxWithIconButton
import com.whgarcia.notas.ui.components.ColorPickerDialog
import com.whgarcia.notas.ui.components.ContentTextField
import com.whgarcia.notas.ui.components.CustomSnackbar
import com.whgarcia.notas.ui.components.FloatingButton
import com.whgarcia.notas.ui.components.MainTextField
import com.whgarcia.notas.ui.components.dateAndTimeNow
import com.whgarcia.notas.viewmodel.NotasViewModel
import com.whgarcia.notas.viewmodel.StateNotasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    stateNoteVM: StateNotasViewModel,
    navController: NavHostController,
    notasVM: NotasViewModel
){
    val state = stateNoteVM.state
    var isColorPickerVisible by remember { mutableStateOf(false) }  // Estado para controlar la visibilidad del diálogo


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BoxWithIconButton(
                        onClick = { navController.popBackStack() },
                        icon = painterResource(id = R.drawable.ic_arrow_back_24),
                        desc = stringResource(id = R.string.cd_back),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.view_add_note),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    // Botón para mostrar el menú de colores
                    BoxWithIconButton(
                        onClick = { isColorPickerVisible = true }, // Activar el diálogo
                        icon = painterResource(id = R.drawable.ic_palette_24),
                        desc = stringResource(id = R.string.cd_color_note),
                        modifier = Modifier.padding(end = 16.dp),
                        contentColor = state.selectedColor
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    when{
                        state.title.isEmpty() -> {
                            stateNoteVM.showTitleValidation()
                        }
                        state.content.isEmpty() -> {
                            stateNoteVM.showContentValidation()
                        }
                        else -> {
                            notasVM.addNote(
                                Notas(
                                    title = state.title,
                                    content = state.content,
                                    create_date = dateAndTimeNow(),
                                    edit_date = dateAndTimeNow(),
                                    color_note = state.selectedColor.toArgb()
                                )
                            )
                            navController.popBackStack()
                        }
                    }
                },
                icon = painterResource(id = R.drawable.ic_save_24),
                desc = stringResource(id = R.string.cd_save_note)
            )
        },
        snackbarHost = {
            if (state.showTitleError) {
                CustomSnackbar(onClick = { stateNoteVM.showTitleValidation() }, title = stringResource(id = R.string.txt_title_validation))
            }
            if (state.showContentError) {
                CustomSnackbar(onClick = { stateNoteVM.showContentValidation() }, title = stringResource(id = R.string.txt_content_validation))
            }
        }
    ){ innerPadding ->
        AddNoteContent(
            modifier = Modifier.padding(innerPadding),
            state,
            stateNoteVM = stateNoteVM
        )
        // Mostrar el diálogo de selección de color cuando `isColorPickerVisible` sea true
        if (isColorPickerVisible) {
            ColorPickerDialog(
                selectedColor = state.selectedColor,
                onColorSelected = { color ->
                    stateNoteVM.updateSelectedColor(color)  // Actualizar el color seleccionado
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
fun AddNoteContent(
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
            enabled = true
        )
        ContentTextField(
            value = state.content,
            onValueChange = {stateNoteVM.onValue(it, "content")},
            enabled = true
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            stateNoteVM.limpiar()
        }
    }
}