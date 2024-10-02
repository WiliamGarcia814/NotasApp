package com.whgarcia.notas.di

import android.content.Context
import androidx.room.Room
import com.whgarcia.notas.room.NotasDatabase
import com.whgarcia.notas.room.NotasDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesNotesDao(notasDatabase: NotasDatabase) : NotasDatabaseDao{
        return notasDatabase.noteDao()
    }

    @Singleton
    @Provides
    fun providesNotesDatabase(@ApplicationContext context: Context) : NotasDatabase{
        return Room.databaseBuilder(
            context,
            NotasDatabase::class.java,
            "notas.db"
        ).fallbackToDestructiveMigration().build()
    }
}