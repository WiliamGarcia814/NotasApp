package com.whgarcia.notas.repository

import com.whgarcia.notas.model.Notas
import com.whgarcia.notas.room.NotasDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotasRepository @Inject constructor(private val notasDatabaseDao: NotasDatabaseDao) {

    suspend fun addNote(nota: Notas) = notasDatabaseDao.addNote(nota)

    suspend fun updateNote(nota: Notas) = notasDatabaseDao.updateNote(nota)

    suspend fun deleteNote(nota: Notas) = notasDatabaseDao.deleteNote(nota)

    fun getAllNotes(): Flow<List<Notas>> = notasDatabaseDao.getAllNotes().flowOn(Dispatchers.IO).conflate()

    fun getAllDeleteNotes(): Flow<List<Notas>> = notasDatabaseDao.getAllDeleteNotes().flowOn(Dispatchers.IO).conflate()

    fun getNoteById(id: Long): Flow<Notas> = notasDatabaseDao.getNoteById(id).flowOn(Dispatchers.IO).conflate()
}