package com.sngs.swayam.business.adapters.packages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.network.model.Packages.PackageList
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.swayam_packages_item_layout.view.*

class SwayamPackagesListAdapter  (private var arrayList: List<PackageList>, private val context: Context) :
    RecyclerView.Adapter<SwayamPackagesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.swayam_packages_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.swayam_package_txt.setText(""+arrayList.get(position).packageName)

        Links.PackageList_detail_Result = arrayList.get(position).packageDetail

        if(Links.PackageList_detail_Result.size>0)
        {
            val package_detail_txt = holder.itemView.findViewById(R.id.package_detail_txt) as RecyclerView
            package_detail_txt.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            package_detail_txt.adapter = SwayamPackagesDetailListAdapter(Links.PackageList_detail_Result,context)
        }

    }

}