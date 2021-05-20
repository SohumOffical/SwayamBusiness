package com.sngs.swayam.business.adapters.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.notifications.NotificationActivity
import com.sngs.swayam.business.network.model.Notification.Notification
import kotlinx.android.synthetic.main.notification_item_layout.view.*


class NotificationListAdapter  (private var arrayList: List<Notification>, private val context: Context) :
    RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tvTitle.setText(""+arrayList.get(position).notiType)
        holder.itemView.tvSubTitle.setText(""+arrayList.get(position).notiMessage)
        holder.itemView.tv_date.setText("" + arrayList.get(position).getmNotiDate())

        if(arrayList.get(position).notiType.equals("Promotion Query")){
            holder.itemView.iv_replay.visibility = View.VISIBLE
        }
        else{
            holder.itemView.iv_replay.visibility = View.GONE
        }

        holder.itemView.iv_delete.setOnClickListener {
            if (context is NotificationActivity) {
                (context as NotificationActivity).api_calling_for_delete_notification(arrayList.get(position).notiId)
            }
        }

        holder.itemView.iv_replay.setOnClickListener {
            if (context is NotificationActivity) {
                (context as NotificationActivity).open_replay_layout(arrayList.get(position).getmNotiEventId())
            }
        }
    }

}