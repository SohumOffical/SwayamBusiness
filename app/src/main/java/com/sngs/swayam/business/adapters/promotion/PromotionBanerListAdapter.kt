package com.sngs.swayam.business.adapters.promotion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sngs.swayam.business.R
import com.sngs.swayam.business.network.model.PromotionBanner.PromotionBannerListResult
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.promotion_baner_item_layout.view.*

class PromotionBanerListAdapter  (private var arrayList: List<PromotionBannerListResult>, private val context: Context) :
    RecyclerView.Adapter<PromotionBanerListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.promotion_baner_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(arrayList.get(position).isIs_checked){
            holder.itemView.banner_checkbox.isChecked = true;
        }
        else{
            holder.itemView.banner_checkbox.isChecked = false;
        }

        Glide.with(context).load(""+arrayList.get(position).promotionBanner).
            placeholder(context.resources.getDrawable(R.drawable.grdient_primary_color))
            .into(holder.itemView.img_banner);

        holder.itemView.banner_checkbox.setOnClickListener {
            for (i in 0..(arrayList.size-1)) {
                if(i==position){
                    arrayList.get(i).isIs_checked = true
                }
                else{
                    arrayList.get(i).isIs_checked = false
                }
            }
            notifyDataSetChanged()
        }
    }

}