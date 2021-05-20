package com.sngs.swayam.business.adapters.product

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.Category.CategoryListDatum

import kotlinx.android.synthetic.main.product_item_layout.view.*

class ProductListAdapter  (private var arrayList: List<CategoryListDatum>, private val context: Context) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(arrayList.get(position).isIs_selected){
            holder.itemView.product_txt.setBackgroundResource(R.drawable.contact_shape)
            holder.itemView.product_txt.setTextColor(context.resources.getColor(R.color.white))
        }
        else {
            holder.itemView.product_txt.setBackgroundResource(R.drawable.contact_shape_deselected)
            holder.itemView.product_txt.setTextColor(context.resources.getColor(R.color.black))
        }

        holder.itemView.product_txt.setOnClickListener{
            if (arrayList.get(position).isIs_selected.equals(arrayList.get(position).isIs_selected)) {
                if (arrayList.get(position).isIs_selected) {
                    arrayList.get(position).isIs_selected = false
                    Links.selected_category_ids_list.remove(arrayList.get(position).categoryId)
                    Log.e("Seletced_id", " " + arrayList.get(position).categoryId)
                } else {
                    arrayList.get(position).isIs_selected = true
                    Links.selected_category_ids_list.add(arrayList.get(position).categoryId)
                    Log.e("Seletced_id", " " + arrayList.get(position).categoryId)
                }
            }
            notifyDataSetChanged()
            /*if(Links.category_list.get(position).isIs_selected){
                Links.category_list.get(position).setIs_selected(false)
            }
            else{
                Links.category_list.get(position).setIs_selected(true)

            }*/
        }

        holder.itemView.product_txt.setText(""+arrayList.get(position).categoryName)
    }

}