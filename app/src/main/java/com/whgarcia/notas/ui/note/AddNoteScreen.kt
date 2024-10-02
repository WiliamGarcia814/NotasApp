package com.whgarcia.notas.ui.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.whgarcia.notas.R
import com.whgarcia.notas.model.Notas
import com.whgarcia.notas.ui.components.ContentTextField
import com.whgarcia.notas.ui.components.FloatingButton
import com.whgarcia.notas.ui.components.MainTextField
import com.whgarcia.notas.ui.components.dateAndTimeEditNow
import com.whgarcia.notas.ui.components.dateAndTimeNow
import com.whgarcia.notas.viewmodel.NotasViewModel
import com.whgarcia.notas.viewmodel.StateNotasViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    stateNoteVM: StateNotasViewModel,
    navController: NavHostController,
    notasVM: NotasViewModel
){
    val state = stateNoteVM.state

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    MainTextField(value = state.title, onValueChange = { stateNoteVM.onValue(it, "title") })
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    notasVM.addNote(
                        Notas(
                            title = state.title,
                            content = state.content,
                            create_date = state.create_date,
                            edit_date = dateAndTimeEditNow()
                        )
                    )
                    navController.popBackStack()
                },
                icon = painterResource(id = R.drawable.ic_save_24),
                desc = stringResource(id = R.string.cd_save_note))
        }
    ){ innerPadding ->
        AddNoteContent(
            modifier = Modifier.padding(innerPadding),
            stateNoteVM = stateNoteVM
        )
    }
}

@Composable
fun AddNoteContent(modifier: Modifier = Modifier, stateNoteVM: StateNotasViewModel) {
    val state = stateNoteVM.state

    stateNoteVM.onValue(dateAndTimeNow(), "create_date")
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
            Text(text = "Editando")
            Text(text = state.create_date)
        }
        ContentTextField(
            value = state.content,
            onValueChange = {stateNoteVM.onValue(it, "content")}
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            stateNoteVM.limpiar()
        }
    }
}