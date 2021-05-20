package com.sngs.swayam.business.adapters.packages

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.packages.ListPackagesActivity
import com.sngs.swayam.business.network.model.Packages.PackageDetail
import com.sngs.swayam.business.network.model.Packages.PackageList
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.swayam_packages_detail_item_layout.view.*
import kotlinx.android.synthetic.main.swayam_packages_item_layout.view.*

class SwayamPackagesDetailListAdapter  (private var arrayList: List<PackageDetail>, private val context: Context) :
    RecyclerView.Adapter<SwayamPackagesDetailListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.swayam_packages_detail_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(arrayList.get(position).isIs_selected){
            holder.itemView.u_apply_txt.isChecked = true
        }
        else{
            holder.itemView.u_apply_txt.isChecked = false
        }

        holder.itemView.u_customer_txt.setText(""+arrayList.get(position).numberOfCustomer)
        holder.itemView.u_price_txt.setText(""+arrayList.get(position).pricePerPromotion)
        holder.itemView.u_minimum_customer_txt.setText(""+arrayList.get(position).minimumCustomer)
        holder.itemView.u_monthly_payment_txt.setText(""+arrayList.get(position).monthlyPayment)

        holder.itemView.u_apply_txt.setOnClickListener {
            Log.e("product_detail_id"," "+Links.packgae_status)
            if(Links.packgae_status) {
                holder.itemView.u_apply_txt.isChecked = false
            }
            else{
                holder.itemView.u_apply_txt.isChecked = true
                Links.selected_pk_id = arrayList.get(position).getmPackageDetailId().toInt()
                Log.e("product_detail_id"," "+Links.selected_pk_id)
                if (context is ListPackagesActivity) {
                    (context as ListPackagesActivity).set_packge_detail()
                }
            }
        }

    }

}