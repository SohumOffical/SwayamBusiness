package com.sngs.swayam.business.users

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.home.HomeActivity
import com.sngs.swayam.business.activity.home.TermsconditionActivity
import com.sngs.swayam.business.network.model.CustomerSignUp.CustomerSignUpBaseResponse
import com.sngs.swayam.business.network.model.Login.LoginBaseResponse
import com.sngs.swayam.business.network.model.MobileVerify.MobileVerifyBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    var otp : String = ""
    var step_compeleted : String = ""
    var fcm_token : String = ""
    var page_type : String = ""
    var user_type : String = ""

    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_login)

        init()
        firebase_detail()
        click_fun()

        if(checkAndRequestPermissions()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return
            }
        }
    }

    private fun init() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val is_login = sharedPreferences.getString("Is_Login","false")
        if(is_login.equals("true")){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        else  if(is_login.equals("True")){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        page_type = "0"
        login_txt.setTextColor(resources.getColor(R.color.colorPrimary))
        login_txt.underline()
        signup_txt.setTextColor(resources.getColor(R.color.drak_blue))
        tl_Name.visibility= View.GONE
        tl_employe_code.visibility= View.GONE
        user_role_li.visibility= View.GONE
        terms_conidtion_txt.visibility= View.GONE
    }

    fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun click_fun() {
        login_txt.setOnClickListener {
            page_type = "0"
            login_txt.setTextColor(resources.getColor(R.color.colorPrimary))
            login_txt.underline()

            signup_txt.setTextColor(resources.getColor(R.color.drak_blue))
            tl_Name.visibility= View.GONE
            tl_employe_code.visibility= View.GONE
            user_role_li.visibility= View.GONE
            terms_conidtion_txt.visibility= View.GONE
        }

        signup_txt.setOnClickListener {
            page_type = "1"
            signup_txt.setTextColor(resources.getColor(R.color.colorPrimary))
            signup_txt.underline()

            Links.selected_role = "Seller"
            saller_txt.isChecked = true
            service_provider_txt.isChecked = false

            login_txt.setTextColor(resources.getColor(R.color.drak_blue))
            tl_Name.visibility= View.VISIBLE
            tl_employe_code.visibility= View.VISIBLE
            user_role_li.visibility= View.VISIBLE
            terms_conidtion_txt.visibility= View.VISIBLE
        }

        save_fab.setOnClickListener {
            if(page_type.equals("0")){
                login_validation()
            }
            else{
                signup_validation()
            }
        }


        btnsignup.setOnClickListener {
            clear_error()
            et_Mobile.text?.clear()
           // et_Mpin.text?.clear()
            val intent = Intent(this, ChooseRoleActivity::class.java)
            startActivity(intent)
        }

        saller_txt.setOnClickListener {
             if(saller_txt.isChecked){
                 Links.selected_role = "Seller"
                 saller_txt.isChecked = true
                 service_provider_txt.isChecked = false
             }
            else{
                 Links.selected_role = "Service Provider"
                 saller_txt.isChecked = false
                 service_provider_txt.isChecked = true
             }
        }

        service_provider_txt.setOnClickListener {
            if(service_provider_txt.isChecked){
                Links.selected_role = "Service Provider"
                saller_txt.isChecked = false
                service_provider_txt.isChecked = true
            }
            else{
                Links.selected_role = "Seller"
                saller_txt.isChecked = true
                service_provider_txt.isChecked = false
            }
        }

        terms_conidtion_txt.setOnClickListener {
            if(terms_conidtion_txt.isChecked){
                val intent = Intent(this, TermsconditionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun login_validation() {
        if(et_Mobile.text.toString().isEmpty()){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_error)
        }
        else if(et_Mobile.text.toString().length<10){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_lenght_error)
        }
        else{
            clear_error()
            api_calling_for_customer_sign_in()
        }
    }
    private fun clear_error() {
        tl_Mobile.error = null
        tl_Name.error = null
        tl_Mobile.error = null
        tl_employe_code.error = null
    }
    private fun api_calling_for_customer_sign_in() {
        val device_id = Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
        )

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callCustomerSignIn(this, device_id,fcm_token,et_Mobile.text.toString(),
                Links.User_Type)
            .enqueue(object : Callback<LoginBaseResponse> {
                override fun onResponse(call: Call<LoginBaseResponse>, response: Response<LoginBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success

                        if (success_v?.toInt()==1)
                        {
                            if(response.body()?.userData!!.otp!=null){
                                otp = response.body()?.userData!!.otp.toString()
                                step_compeleted = response.body()?.userData!!.stepCompleted.toString()
                            }
                            Links.snack_bar(this@LoginActivity,main_layout,response.body()?.message.toString())
                            move_next_page(response)
                        } else {
                            Links.snack_bar(this@LoginActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@LoginActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<LoginBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@LoginActivity,main_layout, t.message.toString())
                }
            })
    }
    private fun move_next_page(response: Response<LoginBaseResponse>){
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("Auth_ID",response.body()?.userData?.authId)
        editor.putString("Auth_Token",response.body()?.userData?.authToken)
        editor.putString("User_name",response.body()?.userData?.customerName)
        editor.putString("User_no",response.body()?.userData?.customerContactNumber)
        editor.putString("Is_Login","false")
        editor.commit()

        val intent = Intent(this, VerificationActivity::class.java)
        intent.putExtra("page_type","0")
        intent.putExtra("step_completed",""+step_compeleted)
        intent.putExtra("OTP",""+otp)
        startActivity(intent)
    }

    private fun signup_validation() {
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
        else if(!terms_conidtion_txt.isChecked){
            clear_error()
            Links.snack_bar(this@LoginActivity, main_layout, resources.getString(R.string.agree_our_terms_condition))
        }
        else{
            api_calling_for_customer_sign_up()
        }
    }

    private fun api_calling_for_customer_sign_up()
    {
        loading_layout.setVisibility(View.VISIBLE)

        val device_id = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )


        ServiceCall.callCustomerSignUp(this,device_id,fcm_token, Links.User_Type,  Links.selected_role, et_Name.text.toString(), et_Mobile.text.toString(), et_employe_code.text.toString() )
            .enqueue(object : Callback<CustomerSignUpBaseResponse> {
                override fun onResponse(call: Call<CustomerSignUpBaseResponse>, response: Response<CustomerSignUpBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success

                        if (success_v?.toInt()==1)
                        {

                            Links.snack_bar(this@LoginActivity,main_layout,response.body()?.message.toString())

                            val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
                            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                            editor.putString("Auth_ID",response.body()?.customerDetail?.authId.toString())
                            editor.putString("Auth_Token",response.body()?.customerDetail?.authToken)
                            editor.putString("User_name",response.body()?.customerDetail?.customerName)
                            editor.putString("User_no",response.body()?.customerDetail?.customerContactNumber)
                            editor.commit()

                            api_calling_for_mobile_verify()

                        } else {
                            Links.snack_bar(this@LoginActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@LoginActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<CustomerSignUpBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@LoginActivity,main_layout,t.message)
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
                            Links.snack_bar(this@LoginActivity,main_layout,response.body()?.otp.toString())
                            if(response.body()?.otp!=null){
                                otp = response.body()!!.otp.toString()
                            }
                            move_verification_page()
                        } else {
                            Links.snack_bar(this@LoginActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@LoginActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<MobileVerifyBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@LoginActivity,main_layout,t.message)
                }
            })
    }
    private fun move_verification_page(){
        val intent = Intent(this,   VerificationActivity::class.java)
        intent.putExtra("page_type","1")
        intent.putExtra("OTP",""+otp)
        startActivity(intent)
        finish()
    }


    private fun checkAndRequestPermissions(): Boolean {
        val permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val read_permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val call_permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        val camera_Permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val listPermissionsNeeded: MutableList<String> = ArrayList()

        if (camera_Permission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }

        if (call_permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE)
        }

        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (read_permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.e("Permission", "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                val perms: MutableMap<String, Int> = HashMap()
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.CALL_PHONE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                if (grantResults.size > 0) {
                    var i = 0
                    while (i < permissions.size) {
                        perms[permissions[i]] = grantResults[i]
                        i++
                    }
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED &&
                        perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED &&
                        perms[Manifest.permission.CALL_PHONE] == PackageManager.PERMISSION_GRANTED &&
                        perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED){
                        Log.e("Permission", "sms & location services permission granted")
                    } else {
                        Log.e("Permission", "Some permissions are not granted ask again ")
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                        {
                            showDialogOK("Storage and Camera Permission required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                        DialogInterface.BUTTON_NEGATIVE -> {
                                            Snackbar.make(main_layout, "Go to settings and enable permissions" , Snackbar.LENGTH_LONG).show()
                                            withDelay(3000) {}
                                            finish()
                                        }
                                    }
                                })
                        } else {
                            Snackbar.make(main_layout, "Go to settings and enable permissions" , Snackbar.LENGTH_LONG).show()
                            withDelay(1000) {}
                            finish()
                        }
                    }
                }
            }
        }
    }
    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }
    fun withDelay(delay: Long = 1000, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }
    private fun firebase_detail() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
                this
        ) { instanceIdResult: InstanceIdResult ->
            fcm_token = instanceIdResult.token
            Log.e("newToken", fcm_token)
        }
    }

    override fun onResume() {
        super.onResume()
        if(Links.is_agree){
            terms_conidtion_txt.isChecked = true
        }
        else{
            terms_conidtion_txt.isChecked = false
        }
    }
}
