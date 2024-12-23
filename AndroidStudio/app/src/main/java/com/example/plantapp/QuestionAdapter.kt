package com.example.plantapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.models.Quiz

class QuestionAdapter(
    private val questionList: List<Quiz>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    // To track the visibility state of explanations
    private val expandedPositions = mutableSetOf<Int>()

    // ViewHolder class to hold views for each item
    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.question)
        val answerText: TextView = itemView.findViewById(R.id.answer)
        val explanationText: TextView = itemView.findViewById(R.id.explain)
        val dropdownIcon: ImageView = itemView.findViewById(R.id.ivdropdown)
        val ideaIcon: ImageView = itemView.findViewById(R.id.imageView4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_question_item, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = questionList[position]

        // Set question text and answer
        holder.questionText.text = item.questionText
        holder.answerText.text = "Correct Answer: ${item.correctAnswer}"
        holder.explanationText.text = item.explanation

        // Track expanded/collapsed state
        val isExpanded = position in expandedPositions
        holder.explanationText.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.ideaIcon.visibility = if (isExpanded) View.VISIBLE else View.GONE

        // Change the icon based on the expanded state
        if (isExpanded) {
            holder.dropdownIcon.setImageResource(R.drawable.dropdown_up) // Image when expanded (drop-up)
        } else {
            holder.dropdownIcon.setImageResource(R.drawable.dropdown_down) // Image when collapsed (drop-down)
        }

        // Click listener for the dropdown icon
        holder.dropdownIcon.setOnClickListener {
            if (isExpanded) {
                expandedPositions.remove(position) // Collapse the explanation
            } else {
                expandedPositions.add(position) // Expand the explanation
            }
            notifyItemChanged(position) // Refresh the item to reflect changes
        }
    }

    override fun getItemCount(): Int = questionList.size
}
