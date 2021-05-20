package com.sngs.swayam.business.activity.packages

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.R
import com.sngs.swayam.business.adapters.mytractions.MyTractionsAdpater
import com.sngs.swayam.business.adapters.packages.SwayamPackagesListAdapter
import com.sngs.swayam.business.network.model.BaseResponse
import com.sngs.swayam.business.network.model.Packages.GetPackageListBaseResponse
import com.sngs.swayam.business.network.model.TranscationDetail.TranscationDetailBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_list_packages.*
import kotlinx.android.synthetic.main.activity_my_tractions.*
import kotlinx.android.synthetic.main.activity_my_tractions.ivBack
import kotlinx.android.synthetic.main.activity_my_tractions.main_layout
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPackagesActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_list_packages)

        init()
        set_data()
        click_fun()
    }

    private fun init()
    {
        val swayam_package_rv = findViewById(R.id.swayam_package_rv) as RecyclerView
        swayam_package_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun set_data()
    {
        api_calling_for_get_packges()
    }

    private fun click_fun()
    {
        ivBack.setOnClickListener {
            finish()
        }

        save_packages_fab.setOnClickListener{
            if(Links.selected_pk_id!=0){
                api_calling_for_save_packges(Links.selected_pk_id.toString())
            }
        }
    }

    private fun api_calling_for_get_packges()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)
        ServiceCall.callGetPackageList(this, auth_id,auth_token, Links.User_Type)
            .enqueue(object : Callback<GetPackageListBaseResponse> {
                override fun onResponse(call: Call<GetPackageListBaseResponse>, response: Response<GetPackageListBaseResponse>)
                {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful())
                    {
                        val success_v = response.body()?.success
                        Links.PackageList_Result.clear()

                        if (success_v?.toInt()==1)
                        {
                            loading_layout.setVisibility(View.GONE)
                            if(response.body()!!.packageList!=null)
                            {
                                Links.PackageList_Result = response.body()!!.packageList
                                swayam_package_rv.adapter = SwayamPackagesListAdapter(Links.PackageList_Result, this@ListPackagesActivity)
                                set_data_pk()
                            }
                        }
                        else
                        {
                            loading_layout.setVisibility(View.GONE)
                            Links.snack_bar(this@ListPackagesActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    else
                    {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@ListPackagesActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<GetPackageListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@ListPackagesActivity,main_layout, t.message.toString())
                }
            })
    }

    private fun set_data_pk()
    {
        for (i in 0..(Links.PackageList_Result.size - 1))
        {
            if(Links.PackageList_Result.get(i).packageDetail.size>0)
            {
                Links.Temp_PackageList_detail_Result = Links.PackageList_Result.get(i).packageDetail
                for (j in 0..(Links.Temp_PackageList_detail_Result.size - 1))
                {
                    if(Links.Temp_PackageList_detail_Result.get(j).getmIsPurchased().equals("True"))
                    {
                        Links.packgae_status = true
                        Links.selected_pk_id =  Links.PackageList_Result.get(i).packageDetail.get(j).getmPackageDetailId().toInt()
                        Links.PackageList_Result.get(i).packageDetail.get(j).isIs_selected = true
                    }
                    else
                    {
                        Links.PackageList_Result.get(i).packageDetail.get(j).isIs_selected = false
                    }
                }
            }
        }

        if(Links.packgae_status){
            save_packages_fab.visibility = View.GONE
        }else{
            save_packages_fab.visibility = View.VISIBLE
        }
    }

    public fun api_calling_for_save_packges(product_detail_id : String = "")
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)
        ServiceCall.callSaveCustomerPackage(this, auth_id,auth_token, Links.User_Type,product_detail_id)
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>)
                    {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful())
                        {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1)
                            {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@ListPackagesActivity,main_layout,response.body()?.message.toString())
                                withDelay(2000){
                                    finish()
                                }
                            }
                            else
                            {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@ListPackagesActivity,main_layout,response.body()?.message.toString())
                            }
                        }
                        else
                        {
                            loading_layout.setVisibility(View.GONE)
                            Links.snack_bar(this@ListPackagesActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@ListPackagesActivity,main_layout, t.message.toString())
                    }
                })
    }

    public fun set_packge_detail()
    {
        for (i in 0..(Links.PackageList_Result.size - 1))
        {
            if(Links.PackageList_Result.get(i).packageDetail.size>0)
            {
                Links.Temp_PackageList_detail_Result = Links.PackageList_Result.get(i).packageDetail
                for (j in 0..(Links.Temp_PackageList_detail_Result.size - 1))
                {
                    if(Links.selected_pk_id.equals(Links.Temp_PackageList_detail_Result.get(j).getmPackageDetailId().toInt()))
                    {
                        Links.PackageList_Result.get(i).packageDetail.get(j).isIs_selected = true
                    }
                    else
                    {
                        Links.PackageList_Result.get(i).packageDetail.get(j).isIs_selected = false
                    }
                }
            }
        }

        swayam_package_rv.adapter = SwayamPackagesListAdapter(Links.PackageList_Result, this@ListPackagesActivity)
    }


    fun withDelay(delay: Long = 1000, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }
}