package com.example.carapp.data

import android.content.Context
import com.example.carapp.data.model.CarsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"
private const val CARS_SHARED_PREF  = "SHARED_PREF"

class CarsDatabase(
    private val context: Context,
) {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_KEY,
        Context.MODE_PRIVATE
    )

    fun getAllNotes(): List<CarsModel> {
        val gson = Gson()
        val json = sharedPreferences.getString(CARS_SHARED_PREF, null)
        val type : Type = object : TypeToken<ArrayList<CarsModel?>?>(){}.type
        val noteList = gson.fromJson<List<CarsModel>>(json,type)
        return noteList ?: emptyList()
    }
    fun saveNote(carsModel: CarsModel){
        val notes = getAllNotes().toMutableList()
        notes.add(0,carsModel)
        val noteGson = Gson().toJson(notes)
        sharedPreferences
            .edit()
            .putString(CARS_SHARED_PREF, noteGson)
            .apply()
    }
    fun deleteNoteAtIndex(index: Int) {
        val getAllNotes = getAllNotes().toMutableList()
        if (index in 0 until getAllNotes.size) {
            getAllNotes.removeAt(index)
            val noteGsonString = Gson().toJson(getAllNotes)
            sharedPreferences.edit().putString(CARS_SHARED_PREF, noteGsonString).apply()
        }
    }
    fun deleteAllNotesCard() = sharedPreferences.edit().clear().apply()

}