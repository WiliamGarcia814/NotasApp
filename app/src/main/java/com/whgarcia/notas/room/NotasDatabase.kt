package com.whgarcia.notas.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whgarcia.notas.model.Notas

@Database(entities = [Notas::class], version = 1, exportSchema = false)
abstract class NotasDatabase : RoomDatabase(){
    abstract fun noteDao(): NotasDatabaseDao
}