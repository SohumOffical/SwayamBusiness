package com.sngs.swayam.business.adapters.promohistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.sngs.swayam.business.R;
import com.sngs.swayam.business.network.model.CustomerPackages.PackageList;

import java.util.List;


public class PromoHistoryAdpater extends RecyclerView.Adapter<PromoHistoryAdpater.MyViewHolder> {

    List<PackageList> arrayList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView no_of_ads,date_promoted,package_choose,sent_to;
        Button amount;

        public MyViewHolder(View view) {
            super(view);

            no_of_ads = (TextView) view.findViewById(R.id.no_of_ads);
            date_promoted = (TextView) view.findViewById(R.id.date_promoted);
            package_choose = (TextView) view.findViewById(R.id.package_choose);
            sent_to = (TextView) view.findViewById(R.id.sent_to);
            amount = (Button) view.findViewById(R.id.amount);
        }
    }


    public PromoHistoryAdpater(Context context_app, List<PackageList> arrayList) {
        this.context = context_app;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promo_history_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.no_of_ads.setText(""+(position+1));
        holder.date_promoted.setText(""+arrayList.get(position).getPackagePurchasedDate());
        holder.package_choose.setText(""+arrayList.get(position).getPackageName());
        holder.sent_to.setText(""+arrayList.get(position).getNumberOfCustomer());
        holder.amount.setText("View");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}

