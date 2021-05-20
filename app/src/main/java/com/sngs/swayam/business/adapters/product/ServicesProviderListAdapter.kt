package com.sngs.swayam.business.adapters.product

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.product.MoreSubCatogoryActivity
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.ServiceProvider.ServiceListDatum
import kotlinx.android.synthetic.main.service_provider_item_layout.view.*

class ServicesProviderListAdapter  (private var arrayList: List<ServiceListDatum>, private val context: Context) :
    RecyclerView.Adapter<ServicesProviderListAdapter.ViewHolder>() {

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

       holder.itemView.service_name_txt.setText(""+arrayList.get(position).serviceName)

        holder.itemView.service_item_rel.setOnClickListener {
            Links.selected_service_id = arrayList.get(position).serviceId
            val intent = Intent(context, MoreSubCatogoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

    }

}