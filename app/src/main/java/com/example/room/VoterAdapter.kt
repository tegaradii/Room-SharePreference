package com.example.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VoterAdapter(
    private val voters: List<Voter>,
    private val onEditClick: (Voter) -> Unit,
    private val onDeleteClick: (Voter) -> Unit,
    private val onDetailClick: (Voter) -> Unit
) : RecyclerView.Adapter<VoterAdapter.VoterViewHolder>() {

    class VoterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberTextView: TextView = itemView.findViewById(R.id.tv_number)
        val nameTextView: TextView = itemView.findViewById(R.id.tv_voter_name)
        val editButton: ImageView = itemView.findViewById(R.id.btn_edit)
        val deleteButton: ImageView = itemView.findViewById(R.id.btn_delete)
        val detailButton: ImageView = itemView.findViewById(R.id.btn_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_voter, parent, false)
        return VoterViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoterViewHolder, position: Int) {
        val voter = voters[position]
        holder.numberTextView.text = (position + 1).toString()
        holder.nameTextView.text = voter.name

        holder.editButton.setOnClickListener { onEditClick(voter) }
        holder.deleteButton.setOnClickListener { onDeleteClick(voter) }
        holder.detailButton.setOnClickListener { onDetailClick(voter) }
    }

    override fun getItemCount(): Int {
        return voters.size
    }
}
