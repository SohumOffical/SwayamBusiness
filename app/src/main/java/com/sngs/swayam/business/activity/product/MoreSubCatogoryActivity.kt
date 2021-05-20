package com.sngs.swayam.business.activity.product

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.onboarding.details.UploadFileActivity
import com.sngs.swayam.business.activity.onboarding.promos.PromotionDetailActivity
import com.sngs.swayam.business.adapters.product.ProductListAdapter
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.Attribute.GetAttributeListBaseResponse
import com.sngs.swayam.business.network.model.Category.GetCategoryListBaseResponse
import com.sngs.swayam.business.network.model.SubCategory.GetSubCategoryListBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import kotlinx.android.synthetic.main.activity_more_sub_catogory.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreSubCatogoryActivity : AppCompatActivity() {

    var click : Int = 1
    var  txt1 : Boolean= false
    var  txt2 : Boolean= false
    var  txt3 : Boolean= false
    var  txt4 : Boolean= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_more_sub_catogory)

        init()
        click_fun()
        click_product_fun()
    }

    private fun init() {
        click = 1

        val more_sub_catergory_rv = findViewById(R.id.more_sub_catergory_rv) as RecyclerView
        more_sub_catergory_rv.layoutManager = GridLayoutManager(applicationContext, 2)

        set_selected_product()
        api_calling_for_category_list()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }
        next_business_img.setOnClickListener {
            val intent = Intent(this, PromotionDetailActivity::class.java)
            intent.putExtra("page_type","0")
            startActivity(intent)

          /*  val intent = Intent(this, UploadFileActivity::class.java)
            startActivity(intent)*/
        }
    }

    private fun click_product_fun()
    {
        txt_1.setOnClickListener {
            click = 1
            set_selected_product()
        }
        txt_2.setOnClickListener {
            click = 2
            set_selected_product()
        }
        txt_3.setOnClickListener {
            click = 3
            set_selected_product()
        }
        txt_4.setOnClickListener {
            click = 4
            set_selected_product()
        }
    }

    private fun set_selected_product()
    {
        if(click==1){
            if(txt1){
                txt1 = false
                txt_1.setBackgroundResource(R.drawable.contact_shape_deselected)
                txt_1.setTextColor(resources.getColor(R.color.black))
            }
            else{
                txt1 = true
                txt_1.setBackgroundResource(R.drawable.contact_shape)
                txt_1.setTextColor(resources.getColor(R.color.white))
            }
        }
        else  if(click==2){
            if(txt2){
                txt2 = false
                txt_2.setBackgroundResource(R.drawable.contact_shape_deselected)
                txt_2.setTextColor(resources.getColor(R.color.black))
            }
            else{
                txt2 = true
                txt_2.setBackgroundResource(R.drawable.contact_shape)
                txt_2.setTextColor(resources.getColor(R.color.white))
            }
        }
        else  if(click==3){
            if(txt3){
                txt3 = false
                txt_3.setBackgroundResource(R.drawable.contact_shape_deselected)
                txt_3.setTextColor(resources.getColor(R.color.black))
            }
            else{
                txt3 = true
                txt_3.setBackgroundResource(R.drawable.contact_shape)
                txt_3.setTextColor(resources.getColor(R.color.white))
            }
        }
        else{
            if(txt4){
                txt4 = false
                txt_4.setBackgroundResource(R.drawable.contact_shape_deselected)
                txt_4.setTextColor(resources.getColor(R.color.black))
            }
            else{
                txt4 = true
                txt_4.setBackgroundResource(R.drawable.contact_shape)
                txt_4.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    private fun api_calling_for_category_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetCategoryList(this, auth_id, auth_token, Links.User_Type, Links.selected_service_id)
            .enqueue(object : Callback<GetCategoryListBaseResponse> {
                override fun onResponse(call: Call<GetCategoryListBaseResponse>, response: Response<GetCategoryListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.category_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.category_list = response.body()!!.categoryListData

                            for (i in 0..(Links.category_list.size-1)) {
                              Links.category_list.get(i).isIs_selected = false
                            }

                            more_sub_catergory_rv.adapter =
                                ProductListAdapter(
                                    Links.category_list,
                                    this@MoreSubCatogoryActivity
                                )
                        }
                        else {
                            Links.snack_bar(this@MoreSubCatogoryActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@MoreSubCatogoryActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<GetCategoryListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@MoreSubCatogoryActivity,main_layout, t.message)
                }
            })
    }

    private fun api_calling_for_sub_category_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetSubCategoryList(this, auth_id, auth_token, Links.User_Type,"","")
            .enqueue(object : Callback<GetSubCategoryListBaseResponse> {
                override fun onResponse(call: Call<GetSubCategoryListBaseResponse>, response: Response<GetSubCategoryListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.sub_category_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.sub_category_list = response.body()!!.subCategoryListData
                            //service_rv_list.adapter = CategoryListAdapter(Links.sub_category_list, this@MoreSubCategoryActivity)
                        }
                        else {
                            Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                        }
                    } else {
                        Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<GetSubCategoryListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                }
            })
    }

    private fun api_calling_for_attribute_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetAttributeList(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<GetAttributeListBaseResponse> {
                override fun onResponse(call: Call<GetAttributeListBaseResponse>, response: Response<GetAttributeListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.attribute_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.attribute_list = response.body()!!.attributeListData
                            //service_rv_list.adapter = CategoryListAdapter(Links.attribute_list, this@MoreSubCategoryActivity)
                        }
                        else {
                            Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                        }
                    } else {
                        Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<GetAttributeListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                }
            })
    }

}

