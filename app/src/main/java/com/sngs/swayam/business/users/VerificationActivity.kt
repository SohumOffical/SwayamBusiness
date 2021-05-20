package com.sngs.swayam.business.users

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.ProfileDetailActivity
import com.sngs.swayam.business.activity.home.HomeActivity
import com.sngs.swayam.business.activity.onboarding.details.BusinessDetailsActivity
import com.sngs.swayam.business.activity.product.ServicesProvidersActivity
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_verification.*


class VerificationActivity : AppCompatActivity() {

    var otp : String = ""
    var page_type : String = "0"
    var Step_completed : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_verification)

        init()
        click_fun()
    }

    private fun init() {
        page_type = intent.getStringExtra("page_type").toString()
        otp = intent.getStringExtra("OTP").toString()
        Step_completed = intent.getStringExtra("step_completed").toString()

        otp_view.otp = otp
    }

    private fun click_fun() {

        llVerify.setOnClickListener {
            if(otp_view.otp.isEmpty()) {
                Links.snack_bar(this@VerificationActivity,main_layout,""+resources.getString(R.string.empty_otp).toString())
            }
            else  if(!otp_view.otp.equals(otp)) {
                Links.snack_bar(this@VerificationActivity,main_layout,""+resources.getString(R.string.valid_otp).toString())
            }
            else {
                move_next_page()
            }
        }

        ivBack.setOnClickListener {
            finish()
        }

        tvResend.setOnClickListener {

        }
    }

    private fun move_next_page() {
        if(page_type.equals("1")){
            val sharedPreferences: SharedPreferences = getSharedPreferences("Temp_Swayam_App", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("State_id","")
            editor.putString("State_name","")
            editor.putString("City_id","")
            editor.putString("City_name","")
            editor.putString("Servies_id","")
            editor.putString("Servies_name","")
            editor.commit()

            val intent = Intent(this, ProfileDetailActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            if(Step_completed.equals("0")){
                val sharedPreferences: SharedPreferences = getSharedPreferences("Temp_Swayam_App", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("State_id","")
                editor.putString("State_name","")
                editor.putString("City_id","")
                editor.putString("City_name","")
                editor.putString("Servies_id","")
                editor.putString("Servies_name","")
                editor.commit()

                val intent = Intent(this, ProfileDetailActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            else if(Step_completed.equals("1")){
                val intent = Intent(this,  BusinessDetailsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            else{
                val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("Is_Login","true")
                editor.commit()

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }


}
