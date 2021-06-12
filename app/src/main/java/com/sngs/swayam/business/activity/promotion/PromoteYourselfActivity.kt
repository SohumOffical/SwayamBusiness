package com.sngs.swayam.business.activity.promotion

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.onboarding.promos.PromotionManagementActivity
import com.sngs.swayam.business.network.model.BaseResponse
import com.sngs.swayam.business.network.model.Category.GetCategoryListBaseResponse
import com.sngs.swayam.business.network.model.CustomerDetail.CustomerDetailBaseResponse
import com.sngs.swayam.business.network.model.SubCategory.GetSubCategoryListBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import com.sngs.swayam.business.network.webUtlis.Links
import kotlinx.android.synthetic.main.activity_promote_yourself.*
import kotlinx.android.synthetic.main.activity_promote_yourself.ivBack
import kotlinx.android.synthetic.main.activity_promote_yourself.main_layout
import kotlinx.android.synthetic.main.activity_promotion_detail.*
import kotlinx.android.synthetic.main.date_picker_layout.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PromoteYourselfActivity : AppCompatActivity() {

    //date picker varible
    var selected_date: String? = null
    var selected_server_date: String? = null
    var mYear = 0
    var mMonth:Int = 0
    var mDay:Int = 0
    var today_Date: Calendar? = Calendar.getInstance();
    var dateFormatter = SimpleDateFormat("dd-MM-yyyy")
    var server_dateFormatter = SimpleDateFormat("yyyy-MM-dd")

    //product varible
    var selected_service_id = ""
    var selected_service_name = ""
    var selected_category_id = ""
    var selected_category_name = ""
    var selected_sub_category_id = "0"
    var selected_sub_category_name = "0"
    var no_last_days = "30"
    var price = ""
    var visiblity = ""
    var promo_baner_id = ""


    //varibles
    var click_event : Int = 0
    var step_one : Boolean = false
    var step_two : Boolean = false
    var step_three : Boolean = false
    var step_four : Boolean = false
    var step_five : Boolean = false
    var step_six : Boolean = false
    var seekBarValue: Int = 0
    var offer_price : Int = 0
    var additional_offer : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_promote_yourself)

        init()
        set_data()
        click_fun()
    }

    private fun set_data()
    {
        price_rb_txt.isChecked = true
        percent_rb_txt.isChecked = false
        price_percentage_li.visibility = View.GONE
        additional_offer_li.visibility = View.VISIBLE

        api_calling_for_customer_detail()
    }

    private fun init() {

        val today_date: Date = Calendar.getInstance().getTime()
        selected_server_date = server_dateFormatter.format(today_date)
        selected_date = dateFormatter.format(today_date)
        available_date_et.setText(selected_date)

        Links.Days_list.clear()
        Links.Days_list.add("30")

        Links.Range_list.clear()
        Links.Range_list.add("0 to 50")
        Links.Range_list.add("50 to 100")
        Links.Range_list.add("100 to 250")
        Links.Range_list.add("250 to 500")
        Links.Range_list.add("500 to 1000")
        Links.Range_list.add("1000+")


        for (i in 0..(Links.PromotionBannerList.size-1)) {
            Links.PromotionBannerList.get(i).isIs_checked = false
        }

        if (service_sp != null) {
            val arrayAdapter = ArrayAdapter(applicationContext,R.layout.spinnerlayout,R.id.txt_spinner,
                Links.service_sp_list)
            service_sp.adapter = arrayAdapter
            service_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selected_service_id = Links.service_list.get(position).serviceId
                    selected_service_name = Links.service_list.get(position).serviceName
                    api_calling_for_category_list()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }



        if (no_last_days_sp != null) {
            val arrayAdapter = ArrayAdapter(applicationContext,R.layout.spinnerlayout,R.id.txt_spinner,
                Links.Days_list)
            no_last_days_sp.adapter = arrayAdapter
            no_last_days_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    no_last_days = Links.Days_list.get(position)
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

        click_event = 0
        set_click_fun()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }

       /* available_date_et.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date_picker_rel.visibility = View.VISIBLE
                date_picker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                    today_Date?.set(year, monthOfYear, dayOfMonth)
                    selected_date = dateFormatter.format(today_Date?.getTime())
                    selected_server_date = server_dateFormatter.format(today_Date?.getTime())
                    available_date_et.setText(selected_date)
                    date_picker_rel.visibility = View.GONE
                }
            } else {
                val datePickerDialog =
                    DatePickerDialog(applicationContext,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            today_Date?.set(year, monthOfYear, dayOfMonth)
                            selected_date = dateFormatter.format(today_Date?.getTime())
                            selected_server_date = server_dateFormatter.format(today_Date?.getTime())
                            available_date_et.setText(selected_date)
                        }, mYear, mMonth, mDay
                    )
                datePickerDialog.show()
            }
        }*/

        cancel_txt.setOnClickListener {
            date_picker_rel.visibility = View.GONE
        }
        okay_txt.setOnClickListener {
            date_picker_rel.visibility = View.GONE
        }

        img_icon1.setOnClickListener {
            val intent = Intent(this, PromotionBannerActivity::class.java)
            startActivity(intent)
        }

        promo_text_rel.setOnClickListener {
            click_event = 0
            set_click_fun()
        }
        btn_next_first.setOnClickListener {
            validation_1()
        }

        promo_category_rel.setOnClickListener {
            click_event = 1
            set_click_fun()
        }
        btn_next_second.setOnClickListener {
            validation_2()
        }

        promo_pricing_rel.setOnClickListener {
            click_event = 2
            set_click_fun()
        }
        btn_next_three.setOnClickListener {
            validation_3()
        }

        promo_visiblity_rel.setOnClickListener {
            click_event = 3
            set_click_fun()
        }
        btn_next_four.setOnClickListener {
            validation_4()
        }

        upload_promo_rel.setOnClickListener {
            click_event = 4
            set_click_fun()
        }
        btn_next_five.setOnClickListener {
            validation_5()
        }


        preview_promo_rel.setOnClickListener {
            click_event = 5
            set_click_fun()
        }
        btn_next_six.setOnClickListener {
            api_calling_for_add_promotion()
        }

        price_rb_txt.setOnCheckedChangeListener { buttonView, isChecked ->
            if(price_rb_txt.isChecked){
                percent_rb_txt.isChecked = false
                buy_one_get_one_rb_txt.isChecked = false
                price_percentage_li.visibility = View.GONE
                additional_offer_li.visibility = View.VISIBLE
                final_price_et.setText("")
            }
        }

        percent_rb_txt.setOnCheckedChangeListener { buttonView, isChecked ->
            if(percent_rb_txt.isChecked){
                price_rb_txt.isChecked = false
                buy_one_get_one_rb_txt.isChecked = false
                price_percentage_li.visibility = View.VISIBLE
                additional_offer_li.visibility = View.GONE
                final_price_et.setText("")
            }
        }

        buy_one_get_one_rb_txt.setOnClickListener {
            if(buy_one_get_one_rb_txt.isChecked){
                price_rb_txt.isChecked = false
                percent_rb_txt.isChecked = false
                price_percentage_li.visibility = View.GONE
                additional_offer_li.visibility = View.GONE
                buy_one_get_one_free()
            }
        }

        prom_price_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?)
            {
                if(price_rb_txt.isChecked){
                    calculate_flat_price()
                }
                else if (percent_rb_txt.isChecked){
                    calculate_precentage()
                }
                else{
                     buy_one_get_one_free()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        additional_offer_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?)
            {
                if(price_rb_txt.isChecked){
                    if(!s.toString().isEmpty()){
                        additional_offer = s.toString()
                        calculate_flat_price()
                    }
                    else{
                        additional_offer = ""
                        if(prom_price_et.text.toString().isEmpty()){
                            final_price_et.setText("")
                        }
                        else{
                            var price = prom_price_et.text.toString().toInt()
                            final_price_et.setText(""+price)
                        }
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        price_percentage_seekbar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                seekBarValue = progress
                calculate_precentage()

            }
            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })

    }

    private fun buy_one_get_one_free() {
        additional_offer_et.setText("0")
        price_percentage_seekbar.progress = 0

        if(!prom_price_et.text.toString().isEmpty()){
            var original_price = prom_price_et.text.toString().toInt()
            original_price = (original_price /2)
            final_price_et.setText(""+original_price)
            additional_offer_et.setText(""+original_price)
        }
    }

    private fun calculate_flat_price() {
        if(price_rb_txt.isChecked){
            if(!additional_offer.toString().isEmpty()){
                var price = prom_price_et.text.toString().toInt()
                var additional_offer = additional_offer_et.text.toString().toInt()
                if(price>additional_offer)
                {
                    var final_price  = (price - additional_offer).toInt()
                    final_price_et.setText(""+final_price.toString())
                }
                else
                {
                    Links.snack_bar(this@PromoteYourselfActivity,main_layout,resources.getString(R.string.price_offer_error))
                }
            }
            else{

                if(prom_price_et.text.toString().isEmpty()){
                    final_price_et.setText("")
                }
                else{
                    var price = prom_price_et.text.toString().toInt()
                    final_price_et.setText(""+price)
                }
            }
        }
    }

    private fun calculate_precentage() {
        if(prom_price_et.text.toString().isEmpty()){
            final_price_et.setText("")
        }
        else{
            percentage_value_txt.setText(""+seekBarValue+" %")
            var percentage = seekBarValue
            var price = prom_price_et.text.toString().toInt()
            var offer = ((price * percentage)/100).toInt()
            var final_price = (price - offer)
            final_price_et.setText(""+final_price)
        }
    }

    private fun validation_1() {
        if(prom_title_et.text.toString().isEmpty()){
            clear_error()
            prom_title_tl.error = resources.getString(R.string.prom_title_error)
        }
        else{
            clear_error()
            click_event = 1
            step_one = true
            set_click_fun()
        }
    }

    private fun validation_2() {
        clear_error()
        step_two = true
        click_event = 2
        set_click_fun()
    }

    private fun validation_3() {
        if(prom_price_et.text.toString().isEmpty()){
            clear_error()
            price_tl.error = resources.getString(R.string.discount_price_error)
        }
        else{
            clear_error()
            click_event = 3
            step_three = true
            set_click_fun()
        }
    }

    private fun validation_4() {
        if(visiblity.equals("0")){
            clear_error()
            promo_visiblity_tl.error = resources.getString(R.string.promo_visiblity_error)
        }
        else if(!enter_mobile_no_et.text.toString().isEmpty() &&
                enter_mobile_no_et.text.toString().length<10){
            visiblity = ""
            clear_error()
            enter_prom_title_txt.error = resources.getString(R.string.mobile_lenght_error)
        }
        else{
            clear_error()
            click_event = 4
            step_four = true
            set_click_fun()
        }
    }

    private fun validation_5() {
        if(promo_baner_id.isEmpty()){
            clear_error()
            prom_photo_tl.error = resources.getString(R.string.prom_baner_error)
        }
        else{
            clear_error()
            step_five = true
            click_event = 5
            set_click_fun()

            set_perview_data()
        }
    }

    private fun set_perview_data() {
        var offer_price: String = ""
        if(percent_rb_txt.isChecked){
            offer_price  =  percentage_value_txt.text.toString()
        }
        else{
            offer_price =  additional_offer_et.text.toString()
        }

        preview_ptitle_txt.setText(""+prom_title_et.text.toString())
        preview_service_desc_txt.setText(""+prom_desc_et.text.toString())
        preview_original_price_detail_txt.setText(""+prom_price_et.text.toString())
        preview_offer_price_detail_txt.setText(""+offer_price)
        preview_additional_offer_detail_txt.setText(""+final_price_et.text.toString())
        preview_service_detail_txt.setText(""+selected_service_name)
        preview_category_detail_txt.setText(""+selected_category_name)
        preview_sub_category_detail_txt.setText(""+selected_sub_category_name)
    }

    private fun set_click_fun() {
        if(click_event==0){
            step_one_li.visibility = View.VISIBLE
            step_two_li.visibility = View.GONE
            step_three_li.visibility = View.GONE
            step_four_li.visibility = View.GONE
            step_five_li.visibility = View.GONE
            step_six_li.visibility = View.GONE

            //set text color
            step_one_txt.setTextColor(resources.getColor(R.color.white))
            promo_text_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered)

            step_two_txt.setTextColor(resources.getColor(R.color.black))
            promo_category_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_three_txt.setTextColor(resources.getColor(R.color.black))
            promo_pricing_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_four_txt.setTextColor(resources.getColor(R.color.black))
            promo_visiblity_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_five_txt.setTextColor(resources.getColor(R.color.black))
            upload_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_six_txt.setTextColor(resources.getColor(R.color.black))
            preview_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

        }
        else if(click_event==1){
            step_one_li.visibility = View.GONE
            step_two_li.visibility = View.VISIBLE
            step_three_li.visibility = View.GONE
            step_four_li.visibility = View.GONE
            step_five_li.visibility = View.GONE
            step_six_li.visibility = View.GONE

            //set text color
            step_one_txt.setTextColor(resources.getColor(R.color.black))
            promo_text_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_two_txt.setTextColor(resources.getColor(R.color.white))
            promo_category_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered)

            step_three_txt.setTextColor(resources.getColor(R.color.black))
            promo_pricing_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_four_txt.setTextColor(resources.getColor(R.color.black))
            promo_visiblity_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_five_txt.setTextColor(resources.getColor(R.color.black))
            upload_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_six_txt.setTextColor(resources.getColor(R.color.black))
            preview_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

        }
        else if(click_event==2){
            step_one_li.visibility = View.GONE
            step_two_li.visibility = View.GONE
            step_three_li.visibility = View.VISIBLE
            step_four_li.visibility = View.GONE
            step_five_li.visibility = View.GONE
            step_six_li.visibility = View.GONE

            //set text color
            step_one_txt.setTextColor(resources.getColor(R.color.black))
            promo_text_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_two_txt.setTextColor(resources.getColor(R.color.black))
            promo_category_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_three_txt.setTextColor(resources.getColor(R.color.white))
            promo_pricing_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered)

            step_four_txt.setTextColor(resources.getColor(R.color.black))
            promo_visiblity_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_five_txt.setTextColor(resources.getColor(R.color.black))
            upload_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_six_txt.setTextColor(resources.getColor(R.color.black))
            preview_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

        }
        else if(click_event==3){
            step_one_li.visibility = View.GONE
            step_two_li.visibility = View.GONE
            step_three_li.visibility = View.GONE
            step_four_li.visibility = View.VISIBLE
            step_five_li.visibility = View.GONE
            step_six_li.visibility = View.GONE

            //set text color
            step_one_txt.setTextColor(resources.getColor(R.color.black))
            promo_text_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_two_txt.setTextColor(resources.getColor(R.color.black))
            promo_category_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_three_txt.setTextColor(resources.getColor(R.color.black))
            promo_pricing_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_four_txt.setTextColor(resources.getColor(R.color.white))
            promo_visiblity_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered)

            step_five_txt.setTextColor(resources.getColor(R.color.black))
            upload_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_six_txt.setTextColor(resources.getColor(R.color.black))
            preview_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

        }
        else if(click_event==4){
            step_one_li.visibility = View.GONE
            step_two_li.visibility = View.GONE
            step_three_li.visibility = View.GONE
            step_four_li.visibility = View.GONE
            step_five_li.visibility = View.VISIBLE
            step_six_li.visibility = View.GONE

            //set text color
            step_one_txt.setTextColor(resources.getColor(R.color.black))
            promo_text_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_two_txt.setTextColor(resources.getColor(R.color.black))
            promo_category_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_three_txt.setTextColor(resources.getColor(R.color.black))
            promo_pricing_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_four_txt.setTextColor(resources.getColor(R.color.black))
            promo_visiblity_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_five_txt.setTextColor(resources.getColor(R.color.white))
            upload_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered)

            step_six_txt.setTextColor(resources.getColor(R.color.black))
            preview_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)
        }
        else if(click_event==5){
            step_one_li.visibility = View.GONE
            step_two_li.visibility = View.GONE
            step_three_li.visibility = View.GONE
            step_four_li.visibility = View.GONE
            step_five_li.visibility = View.GONE
            step_six_li.visibility = View.VISIBLE

            //set text color
            step_one_txt.setTextColor(resources.getColor(R.color.black))
            promo_text_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_two_txt.setTextColor(resources.getColor(R.color.black))
            promo_category_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_three_txt.setTextColor(resources.getColor(R.color.black))
            promo_pricing_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_four_txt.setTextColor(resources.getColor(R.color.black))
            promo_visiblity_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_five_txt.setTextColor(resources.getColor(R.color.black))
            upload_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered_white)

            step_six_txt.setTextColor(resources.getColor(R.color.white))
            preview_promo_rel.background = resources.getDrawable(R.drawable.app_bg_ractangal_cornered)
        }

        set_view_color()
    }

    private fun set_view_color() {
        if(step_one)
            promo_title_view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        else
            promo_title_view.setBackgroundColor(resources.getColor(R.color.white))


        if(step_two)
            promo_category_view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        else
            promo_category_view.setBackgroundColor(resources.getColor(R.color.white))


        if(step_three)
            promo_pricing_view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        else
            promo_pricing_view.setBackgroundColor(resources.getColor(R.color.white))

        if(step_four)
            promo_visiblity_view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        else
            promo_visiblity_view.setBackgroundColor(resources.getColor(R.color.white))


        if(step_five)
            upload_promo_view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        else
            upload_promo_view.setBackgroundColor(resources.getColor(R.color.white))


        if(step_six)
            six_promo_view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        else
            six_promo_view.setBackgroundColor(resources.getColor(R.color.white))

    }

    private fun validation() {
        if(prom_title_et.text.toString().isEmpty()){
            clear_error()
            prom_title_tl.error = resources.getString(R.string.prom_title_error)
        }
        else if(prom_price_et.text.toString().isEmpty()){
            clear_error()
            price_tl.error = resources.getString(R.string.discount_price_error)
        }
        else if(visiblity.equals("0")){
            clear_error()
            promo_visiblity_tl.error = resources.getString(R.string.promo_visiblity_error)
        }
        else if(!enter_mobile_no_et.text.toString().isEmpty() && enter_mobile_no_et.text.toString().length<10){
            visiblity = ""
            clear_error()
            enter_prom_title_txt.error = resources.getString(R.string.mobile_lenght_error)
        }
        else if(promo_baner_id.isEmpty()){
            clear_error()
            prom_photo_tl.error = resources.getString(R.string.prom_baner_error)
        }
        else{
            clear_error()
            api_calling_for_add_promotion()
        }
    }

    private fun clear_error() {
        prom_title_tl.error = null
        prom_photo_tl.error = null
        promo_visiblity_tl.error = null
        price_tl.error = null
    }

    private fun api_calling_for_category_list() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetCategoryList(this, auth_id, auth_token, Links.User_Type, selected_service_id,"1")
            .enqueue(object : Callback<GetCategoryListBaseResponse> {
                override fun onResponse(call: Call<GetCategoryListBaseResponse>, response: Response<GetCategoryListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.category_list.clear()
                        Links.category_sp_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.category_list = response.body()!!.categoryListData
                            for (i in 0..(Links.category_list.size-1)) {
                                Links.category_sp_list.add(Links.category_list.get(i).categoryName)
                            }

                            set_spiner_catergory()
                        }
                        else {
                            set_spiner_catergory()
                            Links.snack_bar(this@PromoteYourselfActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@PromoteYourselfActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<GetCategoryListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@PromoteYourselfActivity,main_layout, t.message)
                }
            })
    }

    private fun set_spiner_catergory() {
        if (category_sp != null) {
            val arrayAdapter = ArrayAdapter(applicationContext,R.layout.spinnerlayout,R.id.txt_spinner,
                Links.category_sp_list)
            category_sp.adapter = arrayAdapter
            category_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selected_category_id = Links.category_list.get(position).categoryId
                    selected_category_name = Links.category_list.get(position).categoryName
                    api_calling_for_sub_category_list()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    private fun api_calling_for_sub_category_list() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetSubCategoryList(this, auth_id, auth_token, Links.User_Type,selected_service_id,
            selected_category_id)
            .enqueue(object : Callback<GetSubCategoryListBaseResponse> {
                override fun onResponse(call: Call<GetSubCategoryListBaseResponse>, response: Response<GetSubCategoryListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.sub_category_list.clear()
                        Links.sub_category_sp_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.sub_category_list = response.body()!!.subCategoryListData
                            for (i in 0..(Links.sub_category_list.size-1)) {
                                Links.sub_category_sp_list.add(Links.sub_category_list.get(i).subCategoryName)
                            }

                            set_spiner_sub_catergory()
                        }
                        else {
                            set_spiner_sub_catergory()
                        }
                    } else {
                        Links.snack_bar(this@PromoteYourselfActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<GetSubCategoryListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@PromoteYourselfActivity,main_layout, t.message)
                }
            })
    }

    private fun set_spiner_sub_catergory() {
        if (sub_category_sp != null) {
            val arrayAdapter = ArrayAdapter(applicationContext,R.layout.spinnerlayout,R.id.txt_spinner,
                Links.sub_category_sp_list)
            sub_category_sp.adapter = arrayAdapter
            sub_category_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selected_sub_category_id = Links.sub_category_list.get(position).subCategoryId
                    selected_sub_category_name = Links.sub_category_list.get(position).subCategoryName
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    private fun api_calling_for_add_promotion() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        var offer_price: String = ""
        if(percent_rb_txt.isChecked){
            offer_price  =  percentage_value_txt.text.toString()
        }
        else{
            offer_price =  additional_offer_et.text.toString()
        }
        ServiceCall.callAddPromotion(this, auth_id, auth_token, Links.User_Type,
            prom_title_et.text.toString(), selected_service_id,selected_category_id,
            selected_sub_category_id,selected_server_date, no_last_days,prom_price_et.text.toString(),
                offer_price,visiblity,prom_desc_et.text.toString(),
            "",promo_baner_id,enter_mobile_no_et.text.toString(),final_price_et.text.toString())
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            Links.snack_bar(this@PromoteYourselfActivity,main_layout,response.body()?.message.toString())
                            withDelay(1500) {
                              move_next_page()
                            }
                        }
                        else {
                            Links.snack_bar(this@PromoteYourselfActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@PromoteYourselfActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@PromoteYourselfActivity,main_layout,t.message.toString())
                }
            })
    }

    private fun move_next_page() {
        finish()
        val intent = Intent(this, PromotionManagementActivity::class.java)
        startActivity(intent)
    }

    fun withDelay(delay: Long = 1000, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }

    override fun onResume() {
        super.onResume()
        if(Links.PromotionBannerList.size>0){
            for (i in 0..(Links.PromotionBannerList.size-1)) {
                if(Links.PromotionBannerList.get(i).isIs_checked){
                    promo_baner_id = Links.PromotionBannerList.get(i).promotionBannerId
                    Glide.with(applicationContext).load(""+Links.PromotionBannerList.get(i).promotionBanner)
                        .placeholder(resources.getDrawable(R.drawable.grdient_primary_color))
                        .into(img_icon1);


                    Glide.with(applicationContext).load(""+Links.PromotionBannerList.get(i).promotionBanner)
                            .placeholder(resources.getDrawable(R.drawable.grdient_primary_color))
                            .into(promo_banner_img);
                }
            }
        }
    }

    private fun api_calling_for_customer_detail()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")


        ServiceCall.callCustomerDetail(this, auth_id , auth_token , Links.User_Type,auth_id)
                .enqueue(object : Callback<CustomerDetailBaseResponse> {
                    override fun onResponse(call: Call<CustomerDetailBaseResponse>, response: Response<CustomerDetailBaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1) {
                                set_customer_detail(response)
                            }
                            else {
                              //  Links.snack_bar(this@PromoteYourselfActivity,main_layout,response.body()?.message.toString())
                            }
                        } else {
                          //  Links.snack_bar(this@PromotionDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<CustomerDetailBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                       // Links.snack_bar(this@PromotionDetailActivity,main_layout,t.message.toString())
                       // Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                    }
                })
    }

    private fun set_customer_detail(response: Response<CustomerDetailBaseResponse>)
    {
        Links.Promissing_visiblity_list.clear()
        Links.Promissing_visiblity_list.add("0")

        if(response.body()!!.customerResult.getmMinimumCustomer().toInt()>0) {
            Links.Promissing_visiblity_list.add("" + response.body()!!.customerResult.getmMinimumCustomer())
        }

        if (promo_visiblity_sp != null) {
            val arrayAdapter = ArrayAdapter(applicationContext,R.layout.spinnerlayout,R.id.txt_spinner,
                    Links.Promissing_visiblity_list)
            promo_visiblity_sp.adapter = arrayAdapter
            promo_visiblity_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    visiblity  = Links.Promissing_visiblity_list.get(position)
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

}
