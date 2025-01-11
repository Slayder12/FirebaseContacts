package com.example.firebasecontacts.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasecontacts.R
import com.example.firebasecontacts.models.UserModel

class CustomAdapter(private val userList: MutableList<UserModel>) :
RecyclerView.Adapter<CustomAdapter.ItemViewHolder>()
{

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(person: UserModel, position: Int)
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

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = userList[position]

        holder.nameTV.text = user.name
        holder.phoneNumberTV.text = user.phoneNumber

        holder.itemView.setOnClickListener{
            if (onItemClickListener != null){
                onItemClickListener!!.onItemClick(user, position)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }


}