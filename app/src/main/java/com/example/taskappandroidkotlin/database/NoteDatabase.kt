package com.example.taskappandroidkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskappandroidkotlin.dao.NoteDao
import com.example.taskappandroidkotlin.entities.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        var noteDatabase: NoteDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NoteDatabase {
            if (noteDatabase == null) {
                noteDatabase = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "notes.db"
                ).build()

            }
            return noteDatabase!!
        }
    }


}
