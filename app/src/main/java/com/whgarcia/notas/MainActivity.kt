package com.whgarcia.notas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.whgarcia.notas.navigation.NavManager
import com.whgarcia.notas.ui.theme.NotasTheme
import com.whgarcia.notas.viewmodel.NotasViewModel
import com.whgarcia.notas.viewmodel.StateNotasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val stateNoteVM : StateNotasViewModel by viewModels()
        val notasVM : NotasViewModel by viewModels()
        setContent {
            NotasTheme{
                NavManager(stateNoteVM, notasVM)
            }
        }
    }
}