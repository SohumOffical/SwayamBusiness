package com.sngs.swayam.business.activity.onboarding.promos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.home.HomeActivity
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.CustomerDetail.CustomerDetailBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import kotlinx.android.synthetic.main.activity_promotion_detail.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionDetailActivity : AppCompatActivity() {

    var counter_banner : Int = 0 ;
    var speedScroll : Long = 4000;
    private var mImg =
        intArrayOf(
            R.drawable.shop_img,
            R.drawable.shop_img2
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_promotion_detail)

        init()
        click_fun()
    }

    private fun init() {
        tvTitle.text = resources.getString(R.string.lbl_profile_description)

        var page_type = intent.getStringExtra("page_type")
        if(page_type.equals("0")){
            btnCreatePortfolio.visibility = View.VISIBLE
        }
        else{
            btnCreatePortfolio.visibility = View.GONE
        }

        api_calling_for_customer_detail()
    }

    internal inner class WalkAdapter : PagerAdapter() {


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context).inflate(R.layout.layout_viewpager, container, false)

            Glide.with(applicationContext).load(Links.Banner_list.get(position)).into((view.findViewById(R.id.img_banner) as ImageView));

            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return  Links.Banner_list.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as View
        }
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }

        btnCreatePortfolio.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun auto_swipe_banner()
    {
        var  handler_banner = Handler();

        val runnable = object : Runnable {
            override fun run() {
                if(counter_banner <= Links.Banner_list.size){
                    viewpager.setCurrentItem(counter_banner)
                    counter_banner++;
                    handler_banner.postDelayed(this, speedScroll);
                    Log.e("counter_banner",""+counter_banner)
                }
                else{
                    counter_banner = 0;
                    viewpager.setCurrentItem(counter_banner)
                    handler_banner.postDelayed(this, speedScroll);
                    Log.e("counter_banner",""+counter_banner)
                }
            }
        }
        handler_banner.postDelayed(runnable, speedScroll);
    }

    private fun api_calling_for_customer_detail()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callCustomerDetail(this, auth_id , auth_token , Links.User_Type,auth_id)
            .enqueue(object : Callback<CustomerDetailBaseResponse> {
                override fun onResponse(call: Call<CustomerDetailBaseResponse>, response: Response<CustomerDetailBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            set_data(response)
                        }
                        else {
                            Links.snack_bar(this@PromotionDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@PromotionDetailActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<CustomerDetailBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@PromotionDetailActivity,main_layout,t.message.toString())
                    Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                }
            })
    }

    private fun set_data(response: Response<CustomerDetailBaseResponse>)
    {
      /*  Links.Banner_list.clear()

        if(!response.body()!!.customerResult.customerMedia1.isEmpty())
             Links.Banner_list.add(""+ response.body()!!.customerResult.customerMedia1)

        if(!response.body()!!.customerResult.customerMedia2.isEmpty())
            Links.Banner_list.add(""+ response.body()!!.customerResult.customerMedia2)

        if(!response.body()!!.customerResult.customerMedia3.isEmpty())
            Links.Banner_list.add(""+ response.body()!!.customerResult.customerMedia3)

        if(!response.body()!!.customerResult.customerMedia4.isEmpty())
            Links.Banner_list.add(""+ response.body()!!.customerResult.customerMedia4)*/

        shop_name_txt.setText(""+ response.body()!!.customerResult.customerShopName + " , "
                + response.body()!!.customerResult.customerContactNumber)

        shop_detail_txt.setText(""+ response.body()!!.customerResult.customerShopAddress1+","
                + response.body()!!.customerResult.customerShopAddress2+","
                + response.body()!!.customerResult.customerShopArea+","
                + response.body()!!.customerResult.customerShopCity+","
                + response.body()!!.customerResult.customerShopPincode+","
                + response.body()!!.customerResult.customerShopState)

        select_year_et.setText(""+response.body()!!.customerResult.customerShopAbout)
        rate_value_txt.setText("0")
        rate_value.rating = 0f
        vote_value_txt.setText("0 Rating")
        user_name_txt.setText(""+response.body()!!.customerResult.customerName)
        shop_time_txt.setText("Shop Timings: "+response.body()!!.customerResult.customerShopStartTime+"--"+
                response.body()!!.customerResult.customerShopEndTime)

        lunch_time_txt.setText("Lunch Timinigs: "+response.body()!!.customerResult.getmCustomerShopLunchStartTime()+"--"+
                response.body()!!.customerResult.getmCustomerShopLunchEndTime())

        category_et.setText(""+response.body()!!.customerResult.customerServiceName)
       // category_et.setText(""+response.body()!!.customerResult.customerServiceName)

     //   category_et.setText(""+response.body()!!.customerResult.categoryResult)

     /*   val adapter = WalkAdapter()
        viewpager.adapter = adapter
        dots.setViewPager(viewpager)

        auto_swipe_banner();*/
    }
}
