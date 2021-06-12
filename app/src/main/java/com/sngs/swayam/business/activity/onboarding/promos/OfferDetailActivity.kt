package com.sngs.swayam.business.activity.onboarding.promos

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.sngs.swayam.business.R
import com.sngs.swayam.business.network.model.CustomerDetail.CustomerDetailBaseResponse
import com.sngs.swayam.business.network.model.PromoDetail.PromoDetailBaseResponse
import com.sngs.swayam.business.network.model.PromotionList.GetCustomerPromotionListBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_offer_detail.*
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.my_promotion_item_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferDetailActivity : AppCompatActivity()
{
    var selected_pos : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_offer_detail)

        init()
        click_fun()
    }


    private fun init()
    {
        selected_pos = intent.getStringExtra("Selected_pos").toString().toInt()
        api_calling_for_user_promotion_list()
    }


    private fun click_fun()
    {
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun api_calling_for_user_promotion_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

       // (0=deactive, 1=active)
        ServiceCall.calGetCustomerPromotionDetailList(this, auth_id, auth_token, Links.User_Type,"1")
            .enqueue(object : Callback<PromoDetailBaseResponse> {
                override fun onResponse(call: Call<PromoDetailBaseResponse>, response: Response<PromoDetailBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.PromotionList_Result.clear()
                        if (success_v?.toInt()==1)
                        {
                            if(response.body()!!.promotionListResult!=null){
                                Links.PromotionList_Result = response.body()!!.promotionListResult
                                set_data()
                            }
                        }
                        else {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<PromoDetailBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@OfferDetailActivity,main_layout,t.message.toString())
                }
            })
    }


    private fun set_data()
    {

        Glide.with(applicationContext).load(Links.PromotionList_Result.get(selected_pos).promotionAttachment)
                .placeholder(R.drawable.app_logo).into(img_icon1);

        prom_title_txt.setText(""+Links.PromotionList_Result.get(selected_pos).promotionTitle)

        original_price_detail_txt.setText(""+Links.PromotionList_Result.get(selected_pos).promotionPrice)
    //    offer_price_detail_txt.setText("₹"+Links.PromotionList_Result.get(selected_pos).promotionAdditionalOffer)
        additional_offer_detail_txt.setText(""+Links.PromotionList_Result.get(selected_pos).getmPromotionFinalRate())

        category_detail_txt.setText(""+Links.PromotionList_Result.get(selected_pos).categoryName)
        promo_start_date_detail_txt.setText(""+Links.PromotionList_Result.get(selected_pos).promotionStartDate)
        promo_expries_detail_txt.setText(""+Links.PromotionList_Result.get(selected_pos).promotionDaysLimit+" Days")
        promo_code_detail_txt.setText(""+Links.PromotionList_Result.get(selected_pos).getmPromotionCode())
        //whats_app_no_txt.setText(""+Links.PromotionList_Result.get(selected_pos).getmCustomerContactNumber())
        service_desc_txt.setText(""+Links.PromotionList_Result.get(selected_pos).promotionDescription)

        like_txt.setText(""+ Links.PromotionList_Result.get(selected_pos).getmTotalLike())
        dislike_txt.setText(""+ Links.PromotionList_Result.get(selected_pos).getmTotalDeslike())
        viwer_count_txt.setText(""+Links.PromotionList_Result.get(selected_pos).getmTotalViewed())

        if(!Links.PromotionList_Result.get(selected_pos).getmPromotionCouponOfferPrice().equals("0")){
            coupon_offer_txt.visibility = View.VISIBLE
            coupon_offer_txt.setText("Swayam Smart user will get max additional discount : ₹ "+Links.PromotionList_Result.get(selected_pos).getmPromotionCouponOfferPrice())
        }

        val s: String = Links.PromotionList_Result.get(selected_pos).promotionAdditionalOffer.toString()
        val position = s.indexOf("%")
        Log.e("position"," "+position)
        if(position>0){
            offer_price_detail_txt.setText(""+Links.PromotionList_Result.get(selected_pos).promotionAdditionalOffer)
        }else{
            offer_price_detail_txt.setText("₹"+Links.PromotionList_Result.get(selected_pos).promotionAdditionalOffer)
        }


        if(!Links.PromotionList_Result.get(selected_pos).subCategoryName.isEmpty()){
            sub_category_detail_txt.setText(""+ Links.PromotionList_Result.get(selected_pos).subCategoryName)
        }
        else{
            sub_category_li.visibility= View.GONE
        }

        api_calling_for_customer_detail()

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
                                set_data_profile(response)
                            }
                            else {
                                Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<CustomerDetailBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@OfferDetailActivity,main_layout,t.message)
                    }
                })
    }

    private fun set_data_profile(response: Response<CustomerDetailBaseResponse>)
    {
      //  whats_app_no_txt.setText(""+ response.body()!!.customerResult.customerContactNumber)

        working_days_detail_txt.setText(""+ response.body()!!.customerResult.customerShopWorkingDays)

        timings_detail_txt.setText(""+ response.body()!!.customerResult.customerShopStartTime +" - "+
                response.body()!!.customerResult.customerShopEndTime)

        lunch_timings_detail_txt.setText(""+ response.body()!!.customerResult.getmCustomerShopLunchStartTime() +" - "+
                response.body()!!.customerResult.getmCustomerShopLunchEndTime())

        operating_since_detail_txt.setText(""+ response.body()!!.customerResult.customerShopYear)

        other_service_txt.setText("Other Service - "+response.body()!!.customerResult.customerShopAbout)
    }

}