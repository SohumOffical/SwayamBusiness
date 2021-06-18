package com.sngs.swayam.business.activity.redeemcoin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sngs.swayam.business.R
import com.sngs.swayam.business.network.model.BaseResponse
import com.sngs.swayam.business.network.model.RedeemCoin.CustomerPromotionDiscountTransactionListBaseResponse
import com.sngs.swayam.business.network.model.RedeemCoinBaseResponse
import com.sngs.swayam.business.network.model.UserDetail.UserDetailBaseResponse
import com.sngs.swayam.business.network.model.UserDetail.UserResult
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_redeem_coin.*
import kotlinx.android.synthetic.main.activity_redeem_coin.main_layout
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RedeemCoinActivity : AppCompatActivity() {

    var otp : String = ""
    var user_id  : String = ""

    var trans_id : String = "2"
    var payment_method : String = "Phone Pay"
    var click : String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
        );
        setContentView(R.layout.activity_redeem_coin)

        init()
        click_fun()
    }

    private fun init() {
        click = "0"

        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val number = sharedPreferences.getString("User_no","")
        et_Mobile_Number.setText(""+number)

        set_radio_btn()
    }

    private fun click_fun() {
        redeem_ivBack.setOnClickListener {
            finish()
        }
        btnGetCoinBalance.setOnClickListener {
            validation()
        }

        original_purchase_price.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.toString().isEmpty()){
                    actual_selling_price_value_txt.text = "0"
                }
                else{
                    calculate_purchase_price()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })

        btnValidate.setOnClickListener {
            validation_otp()
        }

        phone_pay_txt.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                click = "0"
                set_radio_btn()
            }
        }
        g_pay_txt.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                click = "1"
                set_radio_btn()
            }
        }
        pay_tm_txt.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                click = "2"
                set_radio_btn()
            }
        }
        amazon_pay_txt.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                click = "3"
                set_radio_btn()
            }
        }

        btn_confirm_transaction.setOnClickListener {
            if(trans_id.isEmpty()){
                Links.snack_bar(this@RedeemCoinActivity,main_layout,resources.getString(R.string.lbl_trans_id))
            }
            else  if(payment_method.isEmpty()){
                Links.snack_bar(this@RedeemCoinActivity,main_layout,resources.getString(R.string.lbl_payment_method))
            }
            else{
                api_calling_for_confrim_payment()
            }
        }
    }

    private fun set_radio_btn() {
        phone_pay_txt.isChecked = false
        g_pay_txt.isChecked = false
        pay_tm_txt.isChecked = false
        amazon_pay_txt.isChecked = false

        if(click.equals("0")){
            phone_pay_txt.isChecked = true
            payment_method  = "Phone Pay"
        }
        else   if(click.equals("1")){
            g_pay_txt.isChecked = true
            payment_method  = "G Pay"
        }
        else   if(click.equals("2")){
            pay_tm_txt.isChecked = true
            payment_method  = "Paytm"
        }
        else   if(click.equals("3")){
            amazon_pay_txt.isChecked = true
            payment_method  = "Amazon Pay"
        }
    }

    private fun validation_otp() {
        if(et_Mobile.text.toString().isEmpty()){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_error)
        }
        else if(et_Mobile.text.toString().length<10){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_lenght_error)
        }
        else if(enter_item_name.text.toString().isEmpty()){
            clear_error()
            tl_enter_item_name.error = resources.getString(R.string.error_enter_item_name)
        }
        else if(et_OTP.text.toString().isEmpty()){
            clear_error()
            tl_OTP.error = resources.getString(R.string.pwd_error)
        }
       /* else if(!et_OTP.text.toString().equals(otp)){
            clear_error()
            tl_OTP.error = resources.getString(R.string.valid_otp)
        }*/
        else if(max_discount_value_txt.text.toString().isEmpty()){
            clear_error()
            Links.snack_bar(this@RedeemCoinActivity,main_layout,resources.getString(R.string.app_name_display))
        }
        else  if(et_Mobile_Number.text.toString().isEmpty()){
            clear_error()
            tl_Mobile_Number.error = resources.getString(R.string.mobile_error)
        }
        else if(et_Mobile_Number.text.toString().length<10){
            clear_error()
            tl_Mobile_Number.error = resources.getString(R.string.mobile_lenght_error)
        }
        else{
            clear_error()
            api_calling_for_purchase_promotion_discount()
        }
    }

    private fun validation() {
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
         //   set_data()
            api_calling_for_user_detail()
        }
    }

    private fun clear_error() {
        tl_Mobile.error = null
        tl_OTP.error = null
        tl_Mobile_Number.error = null
        tl_enter_item_name.error = null
    }

    private fun api_calling_for_user_detail() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callUserDetail(this, auth_id, auth_token, Links.User_Type, et_Mobile.text.toString())
                .enqueue(object : Callback<UserDetailBaseResponse> {
                    override fun onResponse(call: Call<UserDetailBaseResponse>, response: Response<UserDetailBaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1)
                            {
                                if(response.body()!!.userResult!=null){
                                   set_data(response.body()!!.userResult)
                                }
                            }
                            else {
                                Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<UserDetailBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@RedeemCoinActivity,main_layout,t.message.toString())
                    }
                })
    }

    private fun set_data(userResult: UserResult?) {
        user_coin_balance__value_txt.setText(""+userResult!!.getmUserAvailableCoins())
        user_id = userResult.userNumber

        var coin_value = user_coin_balance__value_txt.text.toString().toInt()
        if(user_current_level_value_txt.text.toString().equals("Silver")){
            var mini_balance = (50 * 6)
            minimum_purchase_price_value_txt.text = mini_balance.toString()

            if(coin_value>=50){
                max_discount_value_txt.text = "50"
            }
            else if(coin_value<20){
                btnValidate.visibility = View.GONE
                btn_confirm_transaction.visibility = View.GONE
                Links.snack_bar(this@RedeemCoinActivity,main_layout,"Minimum need is 20 coins")
            }
            else{
                max_discount_value_txt.text = coin_value.toString()
            }
        }
        else   if(user_current_level_value_txt.text.toString().equals("Gold")){
            var mini_balance = (100 * 6)
            minimum_purchase_price_value_txt.text = mini_balance.toString()

            if(coin_value>=100){
                max_discount_value_txt.text = "100"
            }
            else if(coin_value<20){
                btnValidate.visibility = View.GONE
                btn_confirm_transaction.visibility = View.GONE
                Links.snack_bar(this@RedeemCoinActivity,main_layout,"Minimum need is 20 coins")
            }
            else{
                max_discount_value_txt.text = coin_value.toString()
            }
        }
        else  {
            var mini_balance = (200 * 6)
            minimum_purchase_price_value_txt.text = mini_balance.toString()

            if(coin_value>=200){
                max_discount_value_txt.text = "200"
            }
            else if(coin_value<20){
                btnValidate.visibility = View.GONE
                btn_confirm_transaction.visibility = View.GONE
                Links.snack_bar(this@RedeemCoinActivity,main_layout,"Minimum need is 20 coins")
            }
            else{
                max_discount_value_txt.text = coin_value.toString()
            }
        }


        if(!original_purchase_price.text.toString().isEmpty() && !max_discount_value_txt.text.toString().isEmpty()){
            var coin_value = user_coin_balance__value_txt.text.toString().toInt()
            if(coin_value>=20){
                var original_price = original_purchase_price.text.toString().toInt()
                var discount_value = max_discount_value_txt.text.toString().toInt()

                if(original_price>=discount_value){
                    original_price = original_price - discount_value
                    actual_selling_price_value_txt.text = original_price.toString()

                    var mini_purchase_price = minimum_purchase_price_value_txt.text.toString().toInt()

                    if(original_price>=mini_purchase_price){
                        otp_validation_layout.visibility = View.VISIBLE
                    }
                    else{
                        otp_validation_layout.visibility = View.GONE
                        Links.snack_bar(this@RedeemCoinActivity,main_layout,"Please purchase above ₹"+minimum_purchase_price_value_txt.text.toString())
                    }
                }
                else{
                    otp_validation_layout.visibility = View.GONE
                    actual_selling_price_value_txt.text = "0"
                }
            }
        }

      /*  if(coin_value>=20){
            var actual_price = actual_selling_price_value_txt.text.toString().toInt()
            var discount_value = max_discount_value_txt.text.toString().toInt()
            actual_price = actual_price - discount_value

            actual_selling_price_value_txt.text = actual_price.toString()
        }*/
    }

    private fun calculate_purchase_price() {
        if(user_coin_balance__value_txt.text.toString().isEmpty()){ }
        else  if(original_purchase_price.text.toString().isEmpty()){ }
        else  if(max_discount_value_txt.text.toString().isEmpty()){ }
        else{
            var coin_value = user_coin_balance__value_txt.text.toString().toInt()
            if(coin_value>=20){
                var original_price = original_purchase_price.text.toString().toInt()
                var discount_value = max_discount_value_txt.text.toString().toInt()

                if(original_price>=discount_value){
                    original_price = original_price - discount_value
                    actual_selling_price_value_txt.text = original_price.toString()
                }
                else{
                    actual_selling_price_value_txt.text = "0"
                }
                var mini_purchase_price = minimum_purchase_price_value_txt.text.toString().toInt()

                if(original_price>=mini_purchase_price){
                    otp_validation_layout.visibility = View.VISIBLE
                }
                else{
                    otp_validation_layout.visibility = View.GONE
                    Links.snack_bar(this@RedeemCoinActivity,main_layout,"Please purchase above ₹"+minimum_purchase_price_value_txt.text.toString())
                }
            }
        }
    }

    private fun api_calling_for_purchase_promotion_discount() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)


        ServiceCall.callPromotionPurchaseDiscount(this, auth_id, auth_token, Links.User_Type, user_id,
            et_Mobile.text.toString(),et_OTP.text.toString(),max_discount_value_txt.text.toString(),
            et_Mobile_Number.text.toString(),enter_item_name.text.toString(),original_purchase_price.text.toString(),
            actual_selling_price_value_txt.text.toString())
            .enqueue(object : Callback<RedeemCoinBaseResponse> {
                override fun onResponse(call: Call<RedeemCoinBaseResponse>, response: Response<RedeemCoinBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            trans_id = response.body()!!.getmPromotionPurchaseDiscountId().toString()
                            Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                        }
                        else {
                            Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<RedeemCoinBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@RedeemCoinActivity,main_layout,t.message.toString())
                }
            })
    }

    private fun api_calling_for_confrim_payment() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callPromotionConfirmTranscation(this, auth_id, auth_token, Links.User_Type,
            trans_id,payment_method)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                            withDelay(1500){
                                finish()
                            }
                        }
                        else {
                            Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@RedeemCoinActivity,main_layout,t.message.toString())
                }
            })
    }

    fun withDelay(delay: Long = 1000, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }

    private fun api_calling_for_redeemcoin_transaction() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callCustomerPromotionDiscountTransactionList(this, auth_id, auth_token, Links.User_Type)
                .enqueue(object : Callback<CustomerPromotionDiscountTransactionListBaseResponse> {
                    override fun onResponse(call: Call<CustomerPromotionDiscountTransactionListBaseResponse>, response: Response<CustomerPromotionDiscountTransactionListBaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1) {
                                Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                                withDelay(1500){
                                    finish()
                                }
                            }
                            else {
                                Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@RedeemCoinActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<CustomerPromotionDiscountTransactionListBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@RedeemCoinActivity,main_layout,t.message.toString())
                    }
                })

    }
}