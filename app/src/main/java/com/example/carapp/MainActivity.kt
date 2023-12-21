package com.example.carapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.carapp.data.CarsDatabase
import com.example.carapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sharedPref: CarsDatabase by lazy {
        CarsDatabase(this)
    }

    private val adapter: CarsAdapter by lazy {
        CarsAdapter(
            onDeleteNoteClick = { index ->
                sharedPref.deleteNoteAtIndex(index)
                setupViewsAndAdapter()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewsAndAdapter()
        setOnClickListner()
    }

    private fun setupViewsAndAdapter() {
        val listOfNotes = sharedPref.getAllNotes()
        if (listOfNotes.isNotEmpty()) {
            binding.imageRv.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.updateList(listOfNotes)
            binding.recyclerView.adapter = adapter
        }else{
            binding.imageRv.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    private fun setOnClickListner() = binding.apply {
        addNoteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddCarActivity::class.java))
        }
        deleteCard.setOnClickListener {
            showDeleteNoteDiolog()
        }
    }

    private fun showDeleteNoteDiolog() {
        val alertDiolog = MaterialAlertDialogBuilder(this)
        alertDiolog.setMessage("Do you want to delete all Notes?")
        alertDiolog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alertDiolog.setPositiveButton("Yes") { dialog, _ ->
            deleteAllSavedNotes()
            dialog.dismiss()
        }
        alertDiolog.create().show()
    }

    private fun deleteAllSavedNotes() {
        sharedPref.deleteAllNotesCard()
        adapter.updateList((emptyList()))
        binding.imageRv.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }
}