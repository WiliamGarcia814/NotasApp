package com.whgarcia.notas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whgarcia.notas.ui.home.HomeScreen
import com.whgarcia.notas.ui.note.AddNoteScreen
import com.whgarcia.notas.ui.note.DeleteNoteScreen
import com.whgarcia.notas.ui.note.EditNoteScreen
import com.whgarcia.notas.ui.note.RecoverNoteScreen
import com.whgarcia.notas.viewmodel.NotasViewModel
import com.whgarcia.notas.viewmodel.StateNotasViewModel

@Composable
fun NavManager(stateNoteVM: StateNotasViewModel, notasVM: NotasViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeScreen(navController, notasVM)
        }
        composable("addNote") {
            AddNoteScreen(stateNoteVM, navController, notasVM)
        }
        composable("editNote/{id}", arguments = listOf(
            navArgument("id") {type = NavType.LongType}
        )){
            val id = it.arguments?.getLong("id") ?: 0
            EditNoteScreen(navController, stateNoteVM, id, notasVM)
        }
        composable("deleteNote") {
            DeleteNoteScreen(navController, notasVM)
        }
        composable("recoverNote/{id}", arguments = listOf(
            navArgument("id") {type = NavType.LongType}
        )){
            val id = it.arguments?.getLong("id") ?: 0
            RecoverNoteScreen(navController, stateNoteVM, id, notasVM)
        }
    }
}