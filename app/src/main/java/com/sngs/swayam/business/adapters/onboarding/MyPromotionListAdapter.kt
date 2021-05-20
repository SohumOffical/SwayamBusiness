package com.sngs.swayam.business.adapters.onboarding

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.onboarding.promos.OfferDetailActivity
import com.sngs.swayam.business.network.model.PromoDetail.PromotionListResult
import kotlinx.android.synthetic.main.my_promotion_item_layout.view.*


class MyPromotionListAdapter  (private var arrayList: List<PromotionListResult>, private val context: Context) :
    RecyclerView.Adapter<MyPromotionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_promotion_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(arrayList.get(position).promotionAttachment)
                .placeholder(R.drawable.default_img).into(holder.itemView.shop_img);

        holder.itemView.pro_shop_name_txt.setText(""+arrayList.get(position).promotionTitle)

        holder.itemView.pro_shop_detail_txt.setText(""+arrayList.get(position).promotionDescription)
        holder.itemView.pro_date_txt.setText(""+arrayList.get(position).promotionStartDate)

        holder.itemView.prom_likes_txt.setText(""+context.resources.getString(R.string.likes)+" "+
                arrayList.get(position).getmTotalLike())
        holder.itemView.prom_dislikes_txt.setText(""+context.resources.getString(R.string.dislikes)+" "
                +arrayList.get(position).getmTotalDeslike())
        holder.itemView.viwer_count_txt.setText(""+context.resources.getString(R.string.views)+" "
                +arrayList.get(position).getmTotalViewed())


       /* val word: Spannable = SpannableString("Offer Price:")
        word.setSpan(ForegroundColorSpan(Color.BLACK), 0, word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.itemView.offer_price_txt.setText(word)
        val wordTwo: Spannable = SpannableString(" "+arrayList.get(position).promotionAdditionalOffer)
        wordTwo.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorPrimary)), 0, wordTwo.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)*/
        holder.itemView.offer_price_txt.append("Offer :"+arrayList.get(position).promotionAdditionalOffer)


       /* val word2: Spannable = SpannableString("Final Price:")
        word2.setSpan(ForegroundColorSpan(Color.BLACK), 0, word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.itemView.final_price_txt.setText(word2)
        val wordTwo2: Spannable = SpannableString(" "+arrayList.get(position).getmPromotionFinalRate())
        wordTwo2.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorPrimary)), 0, wordTwo.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)*/
        holder.itemView.final_price_txt.append("Final Price :"+arrayList.get(position).getmPromotionFinalRate())


    /*    val word3: Spannable = SpannableString("Original Price:")
        word3.setSpan(ForegroundColorSpan(Color.BLACK), 0, word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.itemView.original_price_txt.setText(word3)
        val wordTwo3: Spannable = SpannableString(" "+arrayList.get(position).promotionPrice)
        wordTwo3.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorPrimary)), 0, wordTwo.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)*/
        holder.itemView.original_price_txt.append("Original Price :"+arrayList.get(position).promotionPrice)


        holder.itemView.view_more_rel.setOnClickListener {
            val intent = Intent(context, OfferDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("Selected_pos",""+position)
            context.startActivity(intent)
        }

    }

}