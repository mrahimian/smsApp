package com.example.smsapp

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView


class OrderAdapter(
    private val parts: MutableList<String>,
    private val context: Context,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var del: ImageView = itemView.findViewById(R.id.delete)
        var part: EditText = itemView.findViewById(R.id.part)
        var add: ImageView = itemView.findViewById(R.id.add_item)

        init {
            Log.e("$adapterPosition", "position")

            del.setOnClickListener {
                if (adapterPosition != 0) {
                    var count = itemCount
                    parts.clear()
                    for (i in 0 until count) {
                        var view = recyclerView.getChildAt(i)
                        var part: EditText = view.findViewById(R.id.part)
                        var name = part.text.toString()
                        parts.add(name)
                    }
                    parts.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_item, parent, false)

        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OrderAdapter.MyViewHolder, position: Int) {
        val part = parts[position]
        holder.part.setText(part)
        holder.add.setOnClickListener {
            val count = itemCount
            parts.clear()
            for (i in 0 until count) {
                val view = recyclerView.getChildAt(i)
                val part2: EditText = view.findViewById(R.id.part)
                val name = part2.text.toString()
                parts.add(name)
            }
            parts.add("")
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return parts.size
    }
}