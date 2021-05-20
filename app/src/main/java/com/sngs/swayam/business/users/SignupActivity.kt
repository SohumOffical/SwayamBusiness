package com.sngs.swayam.business.users

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.ProfileDetailActivity
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.CustomerSignUp.CustomerSignUpBaseResponse
import com.sngs.swayam.business.network.model.MobileVerify.MobileVerifyBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.main_layout
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    var otp : String = ""
    var fcm_token : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_signup)

        firebase_detail()
        click_fun()
    }

    private fun click_fun() {

        btnContinue.setOnClickListener {
            validation()
        }

        login_in_rel.setOnClickListener {
            finish()
        }

        back_img.setOnClickListener {
            finish()
        }
    }

    private fun validation() {
        if(et_Name.text.toString().isEmpty()){
            clear_error()
            tl_Name.error = resources.getString(R.string.name_error)
        }
        else if(et_Mobile.text.toString().isEmpty()){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_error)
        }
        else if(et_Mobile.text.toString().length<10){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_lenght_error)
        }
        else if(et_employe_code.text.toString().isEmpty()){
            clear_error()
            tl_employe_code.error = resources.getString(R.string.employee_code_error)
        }
        else{
            api_calling_for_customer_sign_up()
        }
    }

    private fun clear_error() {
        tl_Name.error = null
        tl_Mobile.error = null
        tl_employe_code.error = null

    }

    private fun api_calling_for_customer_sign_up()
    {
        loading_layout.setVisibility(View.VISIBLE)

        val device_id = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )


        ServiceCall.callCustomerSignUp(this,device_id, fcm_token, Links.User_Type,
                Links.selected_role, et_Name.text.toString(), et_Mobile.text.toString(),
                et_employe_code.text.toString())
            .enqueue(object : Callback<CustomerSignUpBaseResponse> {
                override fun onResponse(call: Call<CustomerSignUpBaseResponse>, response: Response<CustomerSignUpBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success

                        if (success_v?.toInt()==1)
                        {

                            Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())

                           /* val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
                            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                            editor.putString("Auth_ID",response.body()?.customerDetail?.authId.toString())
                            editor.putString("Auth_Token",response.body()?.customerDetail?.authToken)
                            editor.putString("Is_Login","true")
                            editor.commit()*/

                            api_calling_for_mobile_verify()

                        } else {
                            Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<CustomerSignUpBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@SignupActivity,main_layout,t.message)
                }
            })
    }

    private fun api_calling_for_mobile_verify()
    {
        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callMobileVerify(this, Links.User_Type,  et_Mobile.text.toString() )
            .enqueue(object : Callback<MobileVerifyBaseResponse> {
                override fun onResponse(call: Call<MobileVerifyBaseResponse>, response: Response<MobileVerifyBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success

                        if (success_v?.toInt()==1)
                        {
                            Links.snack_bar(this@SignupActivity,main_layout,response.body()?.otp.toString())
                            if(response.body()?.otp!=null){
                                otp = response.body()!!.otp.toString()
                            }
                            move_next_page()

                        } else {
                            Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<MobileVerifyBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@SignupActivity,main_layout,t.message)
                }
            })
    }

    private fun move_next_page(){
        val intent = Intent(this,   VerificationActivity::class.java)
        intent.putExtra("page_type","1")
        intent.putExtra("OTP",""+otp)
        startActivity(intent)
        finish()
    }

    private fun firebase_detail()
    {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
            this
        ) { instanceIdResult: InstanceIdResult ->
            fcm_token = instanceIdResult.token
            Log.e("newToken", fcm_token)
        }
    }


}
