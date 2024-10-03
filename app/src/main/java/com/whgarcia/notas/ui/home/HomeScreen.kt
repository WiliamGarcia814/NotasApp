package com.whgarcia.notas.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whgarcia.notas.R
import com.whgarcia.notas.ui.components.BoxWithIconButton
import com.whgarcia.notas.ui.components.FloatingButton
import com.whgarcia.notas.ui.components.GridCard
import com.whgarcia.notas.ui.components.ListCard
import com.whgarcia.notas.viewmodel.NotasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, notasVM: NotasViewModel) {
    var expanded by remember { mutableStateOf(false) } // Estado para el menú desplegable
    var mode_view by remember { mutableStateOf(false) } // Estado para el modo de la vista
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    // Bóton para cambiar mode de vista
                    BoxWithIconButton(
                        onClick = { mode_view = !mode_view }, // Cambia el estado de vista al hacer clic,
                        icon = if(mode_view) painterResource(id = R.drawable.ic_view_list_24) else painterResource(id = R.drawable.ic_grid_view_24),
                        desc = if(mode_view) stringResource(id = R.string.view_list) else stringResource(id = R.string.view_grid),
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
                    ){
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete_24),  // Cambia el ícono según sea necesario
                                        contentDescription = stringResource(id = R.string.view_trash),
                                        modifier = Modifier.padding(end = 8.dp)  // Espacio entre el ícono y el texto
                                    )
                                    Text(stringResource(id = R.string.view_trash))
                                }
                            },
                            onClick = {
                                expanded = false
                                navController.navigate("deleteNote")
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    navController.navigate("addNote")
                },
                icon = painterResource(id = R.drawable.ic_add_24),
                desc = stringResource(R.string.cd_add_note)
            )
        }
    ) { innerPadding ->
        HomeContent(
            Modifier.padding(innerPadding),
            notasVM,
            navController,
            mode_view
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    notasVM: NotasViewModel,
    navController: NavHostController,
    isListView: Boolean
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // Obtener la lista de notas del ViewModel
        val notasList by notasVM.notasList.collectAsState()

        if(isListView){
            // Mostrar en modo lista
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(notasList){ item ->
                    ListCard(item = item) {
                        navController.navigate("editNote/${item.id}")
                    }
                }
            }
        }else{
            //Mostrar en modo cuadrícula
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Mostrar 2 columnas para una cuadrícula
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(notasList){ item ->
                    GridCard(item = item) {
                        navController.navigate("editNote/${item.id}")
                    }
                }
            }
        }
    }
}
