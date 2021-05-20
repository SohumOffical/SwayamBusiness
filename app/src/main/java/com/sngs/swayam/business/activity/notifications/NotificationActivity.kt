package com.sngs.swayam.business.activity.notifications

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naimee.swayam.utlis.theme3bottomnavigation.BottomNavigation
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.onboarding.promos.PromotionManagementActivity
import com.sngs.swayam.business.activity.profile.EditProfileActivity
import com.sngs.swayam.business.adapters.notifications.NotificationListAdapter
import com.sngs.swayam.business.network.model.BaseResponse
import com.sngs.swayam.business.network.model.Notification.NotificationBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_notification.ivBack
import kotlinx.android.synthetic.main.activity_notification.main_layout
import kotlinx.android.synthetic.main.activity_offer_detail.*
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.query_detail_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {

    companion object {
        private const val ID_HOME = 1
        private const val ID_MESSAGE = 3
        private const val ID_NOTIFICATION = 4
        private const val ID_ACCOUNT = 5
    }

    var Event_id : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_notification)

        init()
        set_data()
        click_fun()
        set_bottom_navigation()
    }

    private fun init()
    {
        val rv_notification_list = findViewById(R.id.rv_notification_list) as RecyclerView
        rv_notification_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun set_data() {
        api_calling_for_notification()
    }


    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }

        btnContinue_query.setOnClickListener {
            query_detail_layout.visibility = View.GONE

            if(query_detail_txt.text.toString().isEmpty()){
                Links.snack_bar(this@NotificationActivity,main_layout,resources.getString(R.string.empty_query_detail))
            }
            else{
                api_calling_for_replay_notification(query_detail_txt.text.toString())
            }
        }

    }

    private fun set_bottom_navigation() {

        bottomNavigation.add(
            BottomNavigation.Model(
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

            if(name.equals("My ADS")){
                move_next_page(1)
            }

            if(name.equals("HOME")){
                move_next_page(0)
            }

            if(name.equals("ACCOUNT")){
                move_next_page(3)
            }


        }
        Handler().postDelayed(Runnable {
            bottomNavigation.show(ID_NOTIFICATION,true)

        },800)
    }

    private fun move_next_page(page_no : Int) {
        if(page_no==0){
            finish()
        }
        else  if(page_no==1){
            val intent = Intent(this, PromotionManagementActivity::class.java)
            startActivity(intent)
            finish()
        }
        else  if(page_no==3){
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    public fun open_replay_layout(event_id: String =""){
        Event_id = event_id
        query_detail_txt.setText("")
        query_detail_layout.visibility = View.VISIBLE
    }

    private fun api_calling_for_notification()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetNotificationList(this, auth_id,auth_token, Links.User_Type)
                .enqueue(object : Callback<NotificationBaseResponse> {
                    override fun onResponse(call: Call<NotificationBaseResponse>, response: Response<NotificationBaseResponse>)
                    {
                        Links.notification_List.clear()
                        rv_notification_list.adapter = NotificationListAdapter( Links.notification_List,this@NotificationActivity)

                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful())
                        {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1)
                            {
                                loading_layout.setVisibility(View.GONE)

                                if(response.body()!!.notification!=null)
                                {
                                    Links.notification_List = response.body()!!.notification
                                    rv_notification_list.adapter = NotificationListAdapter( Links.notification_List,this@NotificationActivity)
                                }
                            }
                            else
                            {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                            }
                        }
                        else
                        {
                            loading_layout.setVisibility(View.GONE)
                            Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<NotificationBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@NotificationActivity,main_layout, t.message.toString())
                    }
                })
    }

    public  fun api_calling_for_delete_notification(notification_id : String = "")
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callDeleteNotification(this, auth_id,auth_token, Links.User_Type,notification_id)
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>)
                    {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful())
                        {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1) {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                                api_calling_for_notification()
                            }
                            else {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                            }
                        }
                        else
                        {
                            loading_layout.setVisibility(View.GONE)
                            Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@NotificationActivity,main_layout, t.message.toString())
                    }
                })
    }

    public  fun api_calling_for_replay_notification(mes : String = "")
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callPromotionQueryReply(this, auth_id,auth_token, Links.User_Type,Event_id,mes)
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>)
                    {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful())
                        {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1) {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                            }
                            else {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                            }
                        }
                        else
                        {
                            loading_layout.setVisibility(View.GONE)
                            Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@NotificationActivity,main_layout, t.message.toString())
                    }
                })
    }
}
