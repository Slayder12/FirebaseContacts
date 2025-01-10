package com.example.firebasecontacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val personList: MutableList<User>) :
RecyclerView.Adapter<CustomAdapter.ItemViewHolder>()
{

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(person: User, position: Int)
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.nameTV)
        val phoneNumberTV: TextView = itemView.findViewById(R.id.phoneNumberTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = personList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val person = personList[position]

        holder.nameTV.text = person.name
        holder.phoneNumberTV.text = person.phoneNumber

        holder.itemView.setOnClickListener{
            if (onItemClickListener != null){
                onItemClickListener!!.onItemClick(person, position)
            }
        }

    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

}