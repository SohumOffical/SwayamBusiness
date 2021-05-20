package com.sngs.swayam.business.activity.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.naimee.swayam.utlis.theme3bottomnavigation.BottomNavigation
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.notifications.NotificationActivity
import com.sngs.swayam.business.activity.onboarding.promos.PromotionManagementActivity
import com.sngs.swayam.business.network.model.BaseResponse
import com.sngs.swayam.business.network.model.CustomerDetail.CustomerDetailBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import com.yalantis.ucrop.UCrop
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    companion object {
        private const val ID_HOME = 1
        private const val ID_MESSAGE = 3
        private const val ID_NOTIFICATION = 4
        private const val ID_ACCOUNT = 5
    }

    var profile_File : File? = null
    var MAX_ATTACHMENT_COUNT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_edit_profile)


        init()
        set_bottom_navigation()
        click_fun()
    }

    private fun init() {
        api_calling_for_customer_detail()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }
        btnsubmit.setOnClickListener {
            api_calling_for_edit_Profile_detail()
        }

        img2.setOnClickListener {
            onPickPhoto()
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

            if(name.equals("NOTIFICATION")){
                move_next_page(2)
            }

            if(name.equals("HOME")){
                move_next_page(0)
            }
        }
        Handler().postDelayed(Runnable {
            bottomNavigation.show(ID_ACCOUNT,true)

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
        else  if(page_no==2){
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
            finish()
        }

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
                            Links.snack_bar(this@EditProfileActivity,main_edit_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@EditProfileActivity,main_edit_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<CustomerDetailBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@EditProfileActivity,main_edit_layout,t.message.toString())
                }
            })
    }

    private fun api_calling_for_edit_Profile_detail()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callEditProfile(this, auth_id , auth_token , Links.User_Type,
                et_user_Name.text.toString(), et_user_Mobile.text.toString(),profile_File)
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1) {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@EditProfileActivity,main_edit_layout,response.body()?.message.toString())
                                withDelay(2000){
                                    finish()
                                }
                            }
                            else {
                                loading_layout.setVisibility(View.GONE)
                                Links.snack_bar(this@EditProfileActivity,main_edit_layout,response.body()?.message.toString())
                            }
                        } else {
                            loading_layout.setVisibility(View.GONE)
                            Links.snack_bar(this@EditProfileActivity,main_edit_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@EditProfileActivity,main_edit_layout,t.message.toString())
                    }
                })
    }

    private fun set_data(response: Response<CustomerDetailBaseResponse>)
    {
        et_user_Name.setText(""+ response.body()!!.customerResult.customerName)
        et_user_Mobile.setText(""+ response.body()!!.customerResult.customerContactNumber)

        Glide.with(applicationContext).load(response.body()!!.customerResult!!.getmCustomerProfilePicture())
                .placeholder(R.drawable.ic_new_user).into(img2);

        et_user_email.setText(""+ response.body()!!.customerResult.customerEmail)
        et_user_state.setText(""+ response.body()!!.customerResult.customerShopState)
        et_user_cities.setText(""+ response.body()!!.customerResult.customerShopCity)
        et_user_service_area.setText(""+ response.body()!!.customerResult.customerShopArea)

    }


    fun onPickPhoto() {
        Links.selected_image_array_list.clear()
        try {
            val maxCount: Int = MAX_ATTACHMENT_COUNT
            if (Links.selected_image_array_list.size == MAX_ATTACHMENT_COUNT) {
                Toast.makeText(
                        this@EditProfileActivity,
                        "Cannot select more than $MAX_ATTACHMENT_COUNT items",
                        Toast.LENGTH_SHORT
                ).show()
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
        if (resultCode == UCrop.RESULT_ERROR) {
            data?.let { handleCropError(it) };
        }
        else if (requestCode == UCrop.REQUEST_CROP) {
            data?.let { handleCropResult(it) };
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

            profile_File = File(qc_report_string)
            if (profile_File != null) {
                Log.e("", "not null")
                val uri = Uri.fromFile(profile_File)
                startCrop(uri);
               // img2.setImageURI(uri)
            }
            else {
                Log.e("selectedImagePath", "null")
            }
        }
    }

    fun withDelay(delay: Long = 1000, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }

    private fun startCrop(uri: Uri) {
        var destinationFileName: String = "Abc"
        destinationFileName += ".png"
        var uCrop: UCrop = UCrop.of(
            uri,
            Uri.fromFile(File(cacheDir, destinationFileName))
        )
        uCrop.start(this@EditProfileActivity)
    }

    private fun handleCropResult(@NonNull result: Intent) {
        val resultUri: Uri = UCrop.getOutput(result)!!
        if (resultUri != null) {
            profile_File = File(resultUri.getPath())
            img2.setImageURI(resultUri)
        } else {
            Toast.makeText(this@EditProfileActivity, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleCropError(@NonNull result: Intent) {
        val cropError: Throwable = UCrop.getError(result)!!
        if (cropError != null) {
            Log.e("Naimee", "handleCropError: ", cropError)
            Toast.makeText(this@EditProfileActivity, cropError.message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@EditProfileActivity, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show()
        }
    }


}
