package com.sngs.swayam.business.activity.product

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sngs.swayam.business.R
import com.sngs.swayam.business.adapters.product.ServicesProviderListAdapter
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.ServiceProvider.GetServiceProviderBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import kotlinx.android.synthetic.main.activity_services_providers.*
import kotlinx.android.synthetic.main.activity_services_providers.ivBack
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicesProvidersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_services_providers)


        init()
        click_fun()
    }

    private fun init() {
        service_rv_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

        api_calling_for_service_provide_detail()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }
        service_name_li.setOnClickListener {
            val intent = Intent(this, MoreSubCatogoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun api_calling_for_service_provide_detail()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetServiceProviderList(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<GetServiceProviderBaseResponse> {
                override fun onResponse(call: Call<GetServiceProviderBaseResponse>, response: Response<GetServiceProviderBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.service_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.service_list = response.body()!!.serviceListData
                            service_rv_list.adapter =
                                ServicesProviderListAdapter(
                                    Links.service_list,
                                    this@ServicesProvidersActivity
                                )
                        }
                        else {
                            Links.snack_bar(this@ServicesProvidersActivity,main_service_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@ServicesProvidersActivity,main_service_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<GetServiceProviderBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@ServicesProvidersActivity,main_service_layout,t.message.toString())
                }
            })
    }
}