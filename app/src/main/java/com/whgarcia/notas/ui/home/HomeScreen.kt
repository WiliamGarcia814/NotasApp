package com.whgarcia.notas.ui.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whgarcia.notas.R
import com.whgarcia.notas.ui.components.FloatingButton
import com.whgarcia.notas.ui.components.GridCard
import com.whgarcia.notas.viewmodel.NotasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, notasVM: NotasViewModel) {
    var expanded by remember { mutableStateOf(false) } // Estado para el menú desplegable
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    // Botón para mostrar más opciones
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Más opciones")
                    }
                    // Menú desplegable con opciones
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ){
                        DropdownMenuItem(
                            text = { Text("Papelera") },
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
        HomeContent(innerPadding, notasVM, navController)
    }
}

@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    notasVM: NotasViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        // Obtener la lista de notas del ViewModel
        val notasList by notasVM.notasList.collectAsState()
        // Mostrar en modo lista
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(notasList){ item ->
//                ListCard(item = item) {
//
//                }
//            }
//        }
        //Mostrar en modo grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Mostrar 2 columnas para una cuadrícula
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notasList){ item ->
                GridCard(item = item) {
                    navController.navigate("editNote/${item.id}")
                }
            }
        }
    }
}
