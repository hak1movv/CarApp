package com.example.carapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carapp.data.model.CarsModel
import com.example.carapp.databinding.CarItemBinding


class CarsAdapter (
    private val onDeleteNoteClick: (index: Int) -> Unit,

    ): RecyclerView.Adapter<CarsAdapter.MuzucViewHolder>() {

    private val carlist = mutableListOf<CarsModel>()

    fun updateList(notesList: List<CarsModel>) {
        this.carlist.clear()
        this.carlist.addAll(notesList)
        notifyDataSetChanged()
    }

    inner class MuzucViewHolder(
        private val binding: CarItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(carsModel: CarsModel) {
            carlist.indexOf(carsModel)
            binding.noteTxt.text = carsModel.mark
            binding.noteDcn.text = carsModel.model
            binding.deleteIcon.setOnClickListener {
                onDeleteNoteClick.invoke(carlist.indexOf(carsModel))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MuzucViewHolder {
        val binding = CarItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.car_item,
                parent,
                false,
            )
        )
        return MuzucViewHolder(binding)
    }

    override fun getItemCount(): Int = carlist.size

    override fun onBindViewHolder(
        holder: MuzucViewHolder,
        position: Int,
    ) {
        holder.bind(carlist[position])
    }
}