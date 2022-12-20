package com.example.baseapplication.util.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapplication.util.extensions.inflate
import kotlinx.android.extensions.LayoutContainer

class StaticAdapter<T>(private val layout: Int, list: List<T>, private val binder: CommonHolder.(T, Int) -> Unit) : RecyclerView.Adapter<CommonHolder>() {

    private val mutableList = list.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder {
        return parent.inflate(layout).toHolder()
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: CommonHolder, position: Int) {
        val item = mutableList[position]
        holder.binder(item, position)
    }

    fun submitList(newList: List<T>) {
        mutableList.clear()
        mutableList.addAll(newList)
        notifyDataSetChanged()
    }
}

open class CommonHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    override val containerView = itemView
}

fun View.toHolder() = CommonHolder(this)