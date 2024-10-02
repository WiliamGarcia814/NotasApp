package com.whgarcia.notas.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.whgarcia.notas.model.Notas
import kotlinx.coroutines.flow.Flow

@Dao
interface NotasDatabaseDao {

    // Consultar todas las notas no eliminadas
    @Query("SELECT * FROM notas WHERE delete_note = 0")
    fun getAllNotes(): Flow<List<Notas>>

    // Consultar todas las notas eliminadas
    @Query("SELECT * FROM notas WHERE delete_note = 1")
    fun getAllDeleteNotes(): Flow<List<Notas>>

    // Consultar la información de una nota
    @Query("SELECT * FROM notas WHERE id = :id")
    fun getNoteById(id: Long): Flow<Notas>

    // Agregar una nota
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(nota: Notas)

    // Actualizar una nota
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(nota: Notas)

    // Eliminar una nota definitivamente
    @Delete
    suspend fun deleteNote(nota: Notas)
}