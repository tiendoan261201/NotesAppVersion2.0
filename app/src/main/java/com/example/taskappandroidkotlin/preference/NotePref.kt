package com.example.taskappandroidkotlin.preference

import android.content.Context

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskappandroidkotlin.util.Constants
import kotlinx.coroutines.flow.first


class NotePref(var context: Context) {


    companion object{

        val Context.ds : DataStore<Preferences> by preferencesDataStore(Constants.DATA_STORE_USER)
        val KEY_IS_FIRST_USAGE = booleanPreferencesKey("IS_FIRST_USAGE")
    }

    suspend fun setFirstUsage(value: Boolean){
        context.ds.edit {
            it[KEY_IS_FIRST_USAGE] = value
        }
    }
    suspend fun isFirstUsage():Boolean{
        val p = context.ds.data.first()
        return p[KEY_IS_FIRST_USAGE]?:true
    }

}