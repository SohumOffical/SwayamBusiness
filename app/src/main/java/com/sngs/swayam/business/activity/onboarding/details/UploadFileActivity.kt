package com.sngs.swayam.business.activity.onboarding.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.onboarding.promos.PromotionDetailActivity
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.BaseResponse
import com.sngs.swayam.business.network.model.CustomerDetail.CustomerDetailBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import kotlinx.android.synthetic.main.activity_upload_file.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*


class UploadFileActivity : AppCompatActivity() {

    var selected_pos : Int = 0
    var file_type : String = ""
    var no_of_attemps : String = ""
    var MAX_ATTACHMENT_COUNT = 1
    var image_File : File? = null
    var image_File2 : File? = null
    var image_File3 : File? = null
    var image_File4 : File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_upload_file)


        init()
        click_fun()
    }

    private fun init() {
        api_calling_for_customer_detail()
    }

    private fun click_fun()
    {
        ivBack.setOnClickListener {
            finish()
        }
        img_icon1.setOnClickListener {
            selected_pos = 0
            onPickPhoto()
        }
        img_icon2.setOnClickListener {
            selected_pos = 1
            onPickPhoto()
        }
        img_icon3.setOnClickListener {
            selected_pos = 2
            onPickPhoto()
        }
        img_icon4.setOnClickListener {
            selected_pos = 3
            onPickPhoto()
        }
        btnContinue.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        if(image_File==null){
            Links.snack_bar(this@UploadFileActivity,main_layout,""+resources.getString(R.string.file_validation).toString())
        }
        else{
            api_calling_for_upload_file()
        }
    }

    fun onPickPhoto() {
        Links.selected_image_array_list.clear()
        try {
            val maxCount: Int = MAX_ATTACHMENT_COUNT
            if (Links.selected_image_array_list.size == MAX_ATTACHMENT_COUNT) {
                Links.snack_bar(this@UploadFileActivity,main_layout,""+"Cannot select more than $MAX_ATTACHMENT_COUNT items")
            } else {
                FilePickerBuilder.instance
                    .setMaxCount(maxCount)
                    .setSelectedFiles(Links.selected_image_array_list)
                    .setActivityTheme(R.style.FilePickerTheme)
                    .setActivityTitle("Select Image / Video")
                    .showGifs(false)
                    .setMaxCount(1)
                    .enableImagePicker(true)
                    .enableVideoPicker(true)
                    .showFolderView(true)
                    .enableSelectAll(true)
                    .setCameraPlaceholder(R.drawable.ic_camera)
                    .withOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .pickPhoto(this)
            }
        } catch (e: java.lang.Exception) {}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            Links.selected_image_array_list.clear()
            Links.selected_image_array_list.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA)!!)

            addThemToView(Links.selected_image_array_list)
        }

    }

    private fun addThemToView(imagePaths: ArrayList<String>?) {
        val filePaths = ArrayList<String>()

        if (imagePaths != null)
            filePaths.addAll(imagePaths)

        val mStringArray: Array<Any> = filePaths.toTypedArray()

        Log.e("selectedImagePath", "" + imagePaths!!.size)

        for (i in mStringArray.indices) {
            Log.e("selectedImagePath", "" + mStringArray[i] as String)
            val qc_report_string = mStringArray[i] as String

            if(selected_pos==0){
                image_File = File(qc_report_string)
                if (image_File != null) {
                    Log.e("", "not null")
                    val uri = Uri.fromFile(image_File)
                    img_icon1.setImageURI(uri)
                }
                else {
                    Log.e("selectedImagePath", "null")
                }
            }
            else  if(selected_pos==1){
                image_File2 = File(qc_report_string)
                if (image_File2 != null) {
                    Log.e("", "not null")
                    val uri = Uri.fromFile(image_File2)
                    img_icon2.setImageURI(uri)
                }
                else {
                    Log.e("selectedImagePath", "null")
                }
            }
            else  if(selected_pos==2){
                image_File3 = File(qc_report_string)
                if (image_File3 != null) {
                    Log.e("", "not null")
                    val uri = Uri.fromFile(image_File3)
                    img_icon3.setImageURI(uri)
                }
                else {
                    Log.e("selectedImagePath", "null")
                }
            }
            else  if(selected_pos==3){
                image_File4 = File(qc_report_string)
                if (image_File4 != null) {
                    Log.e("", "not null")
                    val uri = Uri.fromFile(image_File4)
                    img_icon4.setImageURI(uri)
                }
                else {
                    Log.e("selectedImagePath", "null")
                }
            }
        }
    }


    private fun api_calling_for_upload_file()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        Links.selected_category_id = ""
        for (i in 0..(Links.selected_category_ids_list.size - 1)) {
            if(Links.selected_category_id.isEmpty()) {
                Links.selected_category_id = Links.selected_category_ids_list.get(i)
            }
            else{
                Links.selected_category_id = Links.selected_category_id+","+Links.selected_category_ids_list.get(i)
            }
        }

        ServiceCall.callUploadFileDetail(this, auth_id, auth_token, Links.User_Type,
            Links.selected_service_id, Links.selected_category_id,"","",image_File,image_File2,image_File3,image_File4)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1)
                        {
                            Links.snack_bar(this@UploadFileActivity,main_layout,response.body()?.message.toString())
                            move_next_page()
                        }
                        else {
                            Links.snack_bar(this@UploadFileActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@UploadFileActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@UploadFileActivity,main_layout,t.message)

                }
            })
    }

    private fun move_next_page() {
        val intent = Intent(this, PromotionDetailActivity::class.java)
        intent.putExtra("page_type","0")
        startActivity(intent)
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
                            Links.snack_bar(this@UploadFileActivity,main_layout,response.body()?.message.toString())
                        }
                        else {
                            Links.snack_bar(this@UploadFileActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@UploadFileActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<CustomerDetailBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@UploadFileActivity,main_layout,t.message)
                }
            })
    }
}
