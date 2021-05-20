package com.sngs.swayam.business.activity.onboarding.promos

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.adapters.onboarding.MyPromotionListAdapter
import com.sngs.swayam.business.adapters.onboarding.PromotionDraftListAdapter
import com.sngs.swayam.business.network.model.PromoDetail.PromoDetailBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_promotion_detail.*
import kotlinx.android.synthetic.main.activity_promotion_detail.ivBack
import kotlinx.android.synthetic.main.activity_promotion_draft.*
import kotlinx.android.synthetic.main.activity_promotion_management.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionDraftActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_promotion_draft)

        init()
        click_fun()

    }

    private fun init() {

        val promotion_detail_rv = findViewById(R.id.promotion_detail_rv) as RecyclerView
        promotion_detail_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        api_calling_for_getCustomer_promotion()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }

    }

    private fun api_calling_for_getCustomer_promotion()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        // (0=deactive, 1=active)
        ServiceCall.calGetCustomerPromotionDetailList(this, auth_id,auth_token, Links.User_Type,"0")
            .enqueue(object : Callback<PromoDetailBaseResponse> {
                override fun onResponse(call: Call<PromoDetailBaseResponse>, response: Response<PromoDetailBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.PromotionList_Result.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.PromotionList_Result = response.body()!!.promotionListResult
                            promotion_detail_rv.adapter = PromotionDraftListAdapter(
                                Links.PromotionList_Result, this@PromotionDraftActivity)
                        }
                        else {
                            Links.snack_bar(this@PromotionDraftActivity,main_draft_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@PromotionDraftActivity,main_draft_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<PromoDetailBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@PromotionDraftActivity,main_draft_layout, t.message.toString())
                }
            })
    }


}