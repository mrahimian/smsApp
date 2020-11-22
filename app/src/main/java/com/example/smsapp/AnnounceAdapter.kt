package com.example.smsapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class AnnounceAdapter(
    private val parts: MutableList<RequestInfo>,
    private val context: Context,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<AnnounceAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var del: ImageView = itemView.findViewById(R.id.remove)
        var good: EditText = itemView.findViewById(R.id.kala)
        var price: EditText = itemView.findViewById(R.id.price)

        init {

            del.setOnClickListener {
                if (adapterPosition != 0) {
                    var count = itemCount
                    parts.clear()
                    for (i in 0 until count) {
                        var view = recyclerView.getChildAt(i)
                        var part: EditText = view.findViewById(R.id.kala)
                        var name = part.text.toString()
                        parts.add(RequestInfo(name,""))
                    }
                    parts.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnnounceAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_price, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnnounceAdapter.MyViewHolder, position: Int) {
        val part = parts[position]
        holder.good.setText(part.part)
    }

    override fun getItemCount(): Int {
        return parts.size
    }

}