package com.sngs.swayam.business.activity.promotion

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sngs.swayam.business.R
import com.sngs.swayam.business.adapters.promotion.PromotionBanerListAdapter
import com.sngs.swayam.business.network.model.PromotionBanner.PromotionBannerBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_promotion_banner.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionBannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_promotion_banner)

        init()
        click_fun()
    }

    private fun init()
    {
        val prmotion_banner_rv = findViewById(R.id.prmotion_banner_rv) as RecyclerView
        prmotion_banner_rv.layoutManager = GridLayoutManager(this, 1)

        api_calling_for_promotion_list()
    }

    private fun click_fun()
    {
        ivBack.setOnClickListener {
            finish()
        }

        next_business_img.setOnClickListener {
            finish()
        }
    }


    private fun api_calling_for_promotion_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callgetCustomerPromotionList(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<PromotionBannerBaseResponse> {
                override fun onResponse(call: Call<PromotionBannerBaseResponse>, response: Response<PromotionBannerBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.PromotionBannerList.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.PromotionBannerList = response.body()!!.promotionBannerListResult
                            for (i in 0..(Links.PromotionBannerList.size-1)) {
                                Links.PromotionBannerList.get(i).isIs_checked = false
                            }

                            prmotion_banner_rv.adapter = PromotionBanerListAdapter( Links.PromotionBannerList,this@PromotionBannerActivity)
                        }
                        else {
                            Links.snack_bar(this@PromotionBannerActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@PromotionBannerActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<PromotionBannerBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@PromotionBannerActivity,main_layout,t.message.toString())
                }
            })
    }
}
