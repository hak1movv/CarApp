package com.example.carapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carapp.data.CarsDatabase
import com.example.carapp.data.model.CarsModel
import com.example.carapp.databinding.AddNoteBinding
import com.google.android.material.snackbar.Snackbar

class AddCarActivity : AppCompatActivity() {

    private val binding: AddNoteBinding by lazy {
        AddNoteBinding.inflate(layoutInflater)
    }

    private val sharePref: CarsDatabase by lazy {
        CarsDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.signOut.setOnClickListener {
            saveNotes()
        }
        binding.iconback.setOnClickListener {
            finish()
        }
    }

    private fun saveNotes() = binding.apply {
        if (markaTxt.text?.isNotEmpty() == true && modelTxt.text?.isNotEmpty() == true) {
            sharePref.saveNote(
                CarsModel(
                    mark = markaTxt.text.toString(),
                    model = modelTxt.text.toString(),
                )
            )
            startActivity(Intent(this@AddCarActivity, MainActivity::class.java))
        } else showToastMessage("Заполните все поля")
    }

    private fun showToastMessage(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}