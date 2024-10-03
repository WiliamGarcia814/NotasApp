package com.whgarcia.notas.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whgarcia.notas.repository.NotasRepository
import com.whgarcia.notas.state.NotasState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StateNotasViewModel @Inject constructor(private val repository: NotasRepository):  ViewModel() {
    var state by mutableStateOf(NotasState())
        private set

    fun onValue(value: String, type: String){
        when(type){
            "title" -> state = state.copy(title = value)
            "content" -> state = state.copy(content = value)
            "create_date" -> state = state.copy(create_date = value)
            "edit_date" -> state = state.copy(edit_date = value)
        }
    }

    fun toggleEditMode(){
        state = state.copy(edit = !state.edit)
    }

    fun getNoteById(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNoteById(id).collect{ item ->
                if (item != null){
                    state = state.copy(
                        title = item.title,
                        content = item.content,
                        create_date = item.create_date,
                        edit_date = item.edit_date,
                        color = item.color_note
                    )
                }else{
                    Log.d("StateNotasViewModel", "Error: Objeto nulo")
                }
            }
        }
    }

    fun limpiar(){
        state = state.copy(
            title = "",
            content = "",
            create_date = "",
            edit_date = "",
            edit = false,
            color = 0
        )
    }
}