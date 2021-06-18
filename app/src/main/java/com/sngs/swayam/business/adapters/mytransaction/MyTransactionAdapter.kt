package com.sngs.swayam.business.adapters.mytransaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.network.model.RedeemCoin.PromotionDiscountTransactionListResult
import kotlinx.android.synthetic.main.my_transaction_item_layout.view.*


class MyTransactionAdapter  (private var arrayList: List<PromotionDiscountTransactionListResult>, private val context: Context) :
    RecyclerView.Adapter<MyTransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_transaction_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.itemView.user_name_txt.setText(""+arrayList.get(position).muserName)
       holder.itemView.user_number_txt.setText(""+arrayList.get(position).userContactNumber)
       holder.itemView.item_name_txt.setText(""+arrayList.get(position).itemName)

        if(!arrayList.get(position).itemPurchasePrice.isEmpty()){
            holder.itemView.purchase_price_txt.setText(""+arrayList.get(position).itemPurchasePrice)
        }else{
            holder.itemView.purchase_price_txt.setText("0")
        }

        if(!arrayList.get(position).itemSellingPrice.isEmpty()){
            holder.itemView.selling_price_txt.setText(""+arrayList.get(position).itemSellingPrice)
        }
        else{
            holder.itemView.selling_price_txt.setText("0")
        }

        if(!arrayList.get(position).promotionDiscount.isEmpty()){
            holder.itemView.max_discount_txt.setText(""+arrayList.get(position).promotionDiscount)
        }
        else{
            holder.itemView.max_discount_txt.setText("0")
        }

        holder.itemView.method_name_txt.setText(""+arrayList.get(position).paymentMethod)
    }

}