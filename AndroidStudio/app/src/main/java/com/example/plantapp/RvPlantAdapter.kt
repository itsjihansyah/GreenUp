package com.example.plantapp

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.databinding.RvPlantsItemBinding
import com.example.plantapp.models.Iot
import com.example.plantapp.models.Plants
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class RvPlantAdapter(private val plantList: ArrayList<Plants>) : RecyclerView.Adapter<RvPlantAdapter.ViewHolder>() {

    private val iotRef = FirebaseDatabase.getInstance().getReference("Iot")


    class ViewHolder(val binding: RvPlantsItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(RvPlantsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bgcolors = listOf(
            R.color.light_yellow,
            R.color.light_blue,
            R.color.light_purple,
            R.color.light_pink
        )

        val indicatorcolors = listOf(
            R.color.yellow,
            R.color.blue,
            R.color.purple,
            R.color.pink
        )

        val backgroundColor = ContextCompat.getColor(holder.itemView.context, bgcolors[position % bgcolors.size])
        val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_yellow)

        if (drawable is GradientDrawable) {
            drawable.setColor(backgroundColor)
            holder.itemView.background = drawable
        }

        val indicatorColor = ContextCompat.getColor(holder.itemView.context, indicatorcolors[position % indicatorcolors.size])
        val indicatorView: View = holder.itemView.findViewById(R.id.tvHealth)

        val indicatorDrawable = indicatorView.background as? GradientDrawable
        indicatorDrawable?.setColor(indicatorColor)  // Change the indicator color

        val currentItem = plantList[position]

        holder.binding.apply {
            tvPlantTitle.text = currentItem.name
            tvPlantType.text = currentItem.type

//          letak error
            val plantId = currentItem.id
            if (plantId != null) {
                iotRef.child(plantId).get()
                    .addOnSuccessListener { snapshot ->
                        val iotHealth = snapshot.getValue(Iot::class.java)

                        if (iotHealth != null) {
                            val health = iotHealth.health ?: "N/A"
                            if (health != "N/A"){
                                val healthWithoutPercent = health.replace("%", "").trim()
                                val healthInt = healthWithoutPercent.toFloat().toInt()

                                holder.binding.tvHealth.text = "$healthInt%"
                            } else {
                                holder.binding.tvHealth.text = health
                            }
                            Log.d("FirebaseSuccess", "Health retrieved: $health")
                        } else {
                            holder.binding.tvHealth.text = "N/A"
                            Log.d("FirebaseError", "Iot data is null for plantId: $plantId")
                        }
                    }
                    .addOnFailureListener { exception ->
                        holder.binding.tvHealth.text = "N/A"
                        Log.e("FirebaseError", "Failed to retrieve health", exception)
                    }
            } else {
                holder.binding.tvHealth.text = "N/A"
                Log.e("FirebaseError", "Plant ID is null")
            }




            rvContainer.setOnClickListener {
                val action = PlantHomeFragmentDirections.actionPlantHomeFragmentToMyPlantDetail(
                    currentItem.id.toString(),
                    currentItem.name.toString(),
                    currentItem.type.toString()
                )
                findNavController(holder.itemView).navigate(action)
            }
        }
    }



}
