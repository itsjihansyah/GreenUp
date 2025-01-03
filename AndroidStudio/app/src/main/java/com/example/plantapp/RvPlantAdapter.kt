package com.example.plantapp

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.databinding.RvPlantsItemBinding
import com.example.plantapp.models.Iot
import com.example.plantapp.models.Plants
import com.google.firebase.database.FirebaseDatabase

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

        val plantImages = listOf(
            R.drawable.cica,
            R.drawable.rock,
            R.drawable.bubu,
            R.drawable.leon
        )

        // Ensure color and image lists are not empty
        if (bgcolors.isEmpty() || indicatorcolors.isEmpty() || plantImages.isEmpty()) {
            Log.e("RvPlantAdapter", "Color or image list is empty")
            return
        }

        val backgroundColor = ContextCompat.getColor(holder.itemView.context, bgcolors[position % bgcolors.size])
        val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_yellow)

        if (drawable is GradientDrawable) {
            drawable.setColor(backgroundColor)
            holder.itemView.background = drawable
        }

        val indicatorColor = ContextCompat.getColor(holder.itemView.context, indicatorcolors[position % indicatorcolors.size])
        val indicatorView: View = holder.itemView.findViewById(R.id.tvHealth)

        val indicatorDrawable = indicatorView.background as? GradientDrawable
        indicatorDrawable?.setColor(indicatorColor) // Safely set the indicator color

        val currentItem = plantList[position]

        holder.binding.apply {
            tvPlantTitle.text = currentItem.name ?: "Unknown"
            tvPlantType.text = currentItem.type ?: "Unknown"

            // Safely handle plant images
            val plantImageResId = plantImages.getOrNull(position % plantImages.size) ?: 0
            if (plantImageResId != 0) {
                ivPlant.setImageResource(plantImageResId)
            } else {
                Log.e("RvPlantAdapter", "Invalid plant image resource ID")
            }

            // Safely handle Firebase data
            val plantId = currentItem.id ?: run {
                Log.e("RvPlantAdapter", "Plant ID is null at position $position")
                tvHealth.text = "N/A"
                return@apply
            }

            iotRef.child(plantId).get()
                .addOnSuccessListener { snapshot ->
                    val iotHealth = snapshot.getValue(Iot::class.java)
                    if (iotHealth != null) {
                        val health = iotHealth.health?.toString() ?: "N/A"
                        try {
                            val healthWithoutPercent = health.replace("%", "").trim()
                            val healthInt = healthWithoutPercent.toFloatOrNull()?.toInt() ?: 0
                            tvHealth.text = "$healthInt%"
                        } catch (e: NumberFormatException) {
                            tvHealth.text = "N/A"
                            Log.e("FirebaseError", "Health value is not a valid number", e)
                        }
                        Log.d("FirebaseSuccess", "Health retrieved: $health")
                    } else {
                        tvHealth.text = "N/A"
                        Log.d("FirebaseError", "IoT data is null for plantId: $plantId")
                    }
                }
                .addOnFailureListener { exception ->
                    tvHealth.text = "N/A"
                    Log.e("FirebaseError", "Failed to retrieve health", exception)
                }

            rvContainer.setOnClickListener {
                try {
                    val action = PlantHomeFragmentDirections.actionPlantHomeFragmentToMyPlantDetail(
                        currentItem.id.toString(),
                        currentItem.name.orEmpty(),
                        currentItem.type.orEmpty()
                    )
                    findNavController(holder.itemView).navigate(action)
                } catch (e: IllegalStateException) {
                    Log.e("RvPlantAdapter", "Navigation failed", e)
                }
            }
        }
    }
}
