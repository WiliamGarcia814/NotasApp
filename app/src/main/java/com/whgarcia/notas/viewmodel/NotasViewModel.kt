package com.whgarcia.notas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whgarcia.notas.model.Notas
import com.whgarcia.notas.repository.NotasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotasViewModel @Inject constructor(private val repository: NotasRepository): ViewModel(){

    private val _notasList = MutableStateFlow<List<Notas>>(emptyList())
    val notasList = _notasList.asStateFlow()

    // StateFlow para almacenar las notas eliminadas
    private val _deletedNotes = MutableStateFlow<List<Notas>>(emptyList())
    val deletedNotes= _deletedNotes.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().collect{ item ->
                if (item.isNullOrEmpty()) {
                    _notasList.value = emptyList()
                }else{
                    _notasList.value = item
                }
            }
        }
    }

    // Función para cargar todas las notas eliminadas
    fun getAllDeletedNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDeleteNotes().collect { item ->
                if (item.isNullOrEmpty()) {
                    _deletedNotes.value = emptyList()
                } else {
                    _deletedNotes.value = item
                }
            }
        }
    }

    fun addNote(nota: Notas) = viewModelScope.launch {
        repository.addNote(nota)
    }

    fun updateNote(nota: Notas) = viewModelScope.launch {
        repository.updateNote(nota)
    }

    fun deleteNote(nota: Notas) = viewModelScope.launch {
        repository.deleteNote(nota)
    }


}