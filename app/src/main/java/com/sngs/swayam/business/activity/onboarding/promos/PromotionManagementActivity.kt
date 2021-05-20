package com.sngs.swayam.business.activity.onboarding.promos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.naimee.swayam.utlis.theme3bottomnavigation.BottomNavigation
import kotlinx.android.synthetic.main.activity_promotion_detail.ivBack
import kotlinx.android.synthetic.main.activity_promotion_management.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.notifications.NotificationActivity
import com.sngs.swayam.business.activity.profile.EditProfileActivity
import com.sngs.swayam.business.adapters.onboarding.MyPromotionListAdapter
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.PromoDetail.PromoDetailBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall


class PromotionManagementActivity : AppCompatActivity() {

    var mobile_no : String = ""
    companion object {
        private const val ID_HOME = 1
        private const val ID_MESSAGE = 3
        private const val ID_NOTIFICATION = 4
        private const val ID_ACCOUNT = 5
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_promotion_management)

        init()
        click_fun()
        set_bottom_navigation()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun init()
    {
        val my_promotion_rv = findViewById(R.id.my_promotion_rv) as RecyclerView
        my_promotion_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        api_calling_for_getCustomer_promotion()
    }

    private fun set_bottom_navigation() {

        bottomNavigation.add(BottomNavigation.Model(
            ID_HOME,
            R.drawable.ic_home
        ))
        bottomNavigation.add(
            BottomNavigation.Model(
            ID_MESSAGE,
            R.drawable.ic_heart
        ))
        bottomNavigation.add(BottomNavigation.Model(
            ID_NOTIFICATION,
            R.drawable.ic_notification
        ))
        bottomNavigation.add(BottomNavigation.Model(
            ID_ACCOUNT,
            R.drawable.ic_account
        ))

        bottomNavigation.setOnShowListener {
            val name = when (it.id) {
                ID_HOME -> "HOME"
                ID_MESSAGE -> "My ADS"
                ID_NOTIFICATION -> "NOTIFICATION"
                ID_ACCOUNT -> "ACCOUNT"
                else -> ""
            }
        }


        bottomNavigation.setOnClickMenuListener {
            val name = when (it.id) {
                ID_HOME -> "HOME"
                ID_MESSAGE -> "My ADS"
                ID_NOTIFICATION -> "NOTIFICATION"
                ID_ACCOUNT -> "ACCOUNT"
                else -> ""
            }

            if(name.equals("HOME")){
                move_next_page(0)
            }

            if(name.equals("NOTIFICATION")){
                move_next_page(2)
            }

            if(name.equals("ACCOUNT")){
                move_next_page(3)
            }


        }
        Handler().postDelayed(Runnable {
            bottomNavigation.show(ID_MESSAGE,true)

        },800)
    }

    private fun move_next_page(page_no : Int) {
        if(page_no==0){
            finish()
        }
        else  if(page_no==2){
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
            finish()
        }
        else  if(page_no==3){
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
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
        ServiceCall.calGetCustomerPromotionDetailList(this, auth_id,auth_token,Links.User_Type,"1")
            .enqueue(object : Callback<PromoDetailBaseResponse> {
                override fun onResponse(call: Call<PromoDetailBaseResponse>, response: Response<PromoDetailBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.PromotionList_Result.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.PromotionList_Result = response.body()!!.promotionListResult
                            my_promotion_rv.adapter = MyPromotionListAdapter(Links.PromotionList_Result,
                            this@PromotionManagementActivity)
                        }
                        else {
                            Links.snack_bar(this@PromotionManagementActivity,main_promotion_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@PromotionManagementActivity,main_promotion_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<PromoDetailBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@PromotionManagementActivity,main_promotion_layout, t.message.toString())
                }
            })
    }


}
