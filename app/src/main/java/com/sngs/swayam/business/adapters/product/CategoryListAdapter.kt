package com.sngs.swayam.business.adapters.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.network.model.Category.CategoryListDatum

import kotlinx.android.synthetic.main.service_provider_item_layout.view.*

class CategoryListAdapter  (private var arrayList: List<CategoryListDatum>, private val context: Context) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.service_provider_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.itemView.service_name_txt.setText(""+arrayList.get(position).categoryName)

    }

}