package com.sngs.swayam.business.activity.onboarding.details

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.product.ServicesProvidersActivity
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.BaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import kotlinx.android.synthetic.main.activity_business_details.*
import kotlinx.android.synthetic.main.activity_business_details.ivBack
import kotlinx.android.synthetic.main.activity_business_details.main_layout
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.number_picker_layout.*
import kotlinx.android.synthetic.main.time_picker_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class BusinessDetailsActivity : AppCompatActivity() {

    var time_click : Int = 0
    var week_click : Int = 0
    var employee_click : Int = 0
    var _24HourSDF = SimpleDateFormat("HH:mm")
    var _12HourSDF = SimpleDateFormat("hh:mm aa")

    var  sun : Boolean= false
    var  mon : Boolean= false
    var  tue : Boolean= false
    var  wed : Boolean= false
    var  thu : Boolean= false
    var  fri : Boolean= false
    var  sat : Boolean= false
    var  days_24 : Boolean= false


    var employee_selection : String = ""
    var working_day_selection : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_business_details)

        init()
        click_fun()
        set_week_fun()
    }

    private fun set_week_fun() {
        btnsun.setOnClickListener {
            week_click = 0
            set_selected_week()
        }

        btnmon.setOnClickListener {
            week_click = 1
            set_selected_week()
        }

        btntue.setOnClickListener {
            week_click = 2
            set_selected_week()
        }

        btnwed.setOnClickListener {
            week_click = 3
            set_selected_week()
        }

        btnthu.setOnClickListener {
            week_click = 4
            set_selected_week()
        }

        btnfri.setOnClickListener {
            week_click = 5
            set_selected_week()
        }

        btnsat.setOnClickListener {
            week_click = 6
            set_selected_week()
        }

        btn_hours.setOnClickListener {
            week_click = 7
            set_selected_week()
        }
        txt_1_5.setOnClickListener {
            employee_click =0
            set_selected_empl()
        }
        txt_5_10.setOnClickListener {
            employee_click =1
            set_selected_empl()
        }
        txt_10_30.setOnClickListener {
            employee_click = 2
            set_selected_empl()
        }
        txt_30_60.setOnClickListener {
            employee_click = 3
            set_selected_empl()
        }
    }

    private fun set_selected_empl() {
        txt_1_5.setBackgroundResource(R.drawable.square_shape)
        txt_1_5.setTextColor(resources.getColor(R.color.colorPrimary))

        txt_5_10.setBackgroundResource(R.drawable.square_shape)
        txt_5_10.setTextColor(resources.getColor(R.color.colorPrimary))

        txt_10_30.setBackgroundResource(R.drawable.square_shape)
        txt_10_30.setTextColor(resources.getColor(R.color.colorPrimary))

        txt_30_60.setBackgroundResource(R.drawable.square_shape)
        txt_30_60.setTextColor(resources.getColor(R.color.colorPrimary))

        if(employee_click==0){
            txt_1_5.setBackgroundResource(R.drawable.solid_square_shape)
            txt_1_5.setTextColor(resources.getColor(R.color.white))

            employee_selection = txt_1_5.text.toString()
        }
        else  if(employee_click==1){
            txt_5_10.setBackgroundResource(R.drawable.solid_square_shape)
            txt_5_10.setTextColor(resources.getColor(R.color.white))

            employee_selection = txt_5_10.text.toString()
        }
        else  if(employee_click==2){
            txt_10_30.setBackgroundResource(R.drawable.solid_square_shape)
            txt_10_30.setTextColor(resources.getColor(R.color.white))

            employee_selection = txt_10_30.text.toString()
        }
        else  if(employee_click==3){
            txt_30_60.setBackgroundResource(R.drawable.solid_square_shape)
            txt_30_60.setTextColor(resources.getColor(R.color.white))

            employee_selection = txt_30_60.text.toString()
        }
    }

    private fun set_selected_week() {

        if(week_click==0){
            if(sun){
                sun = false
                btnsun.setBackgroundResource(R.drawable.circle_with_white_color)
                btnsun.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            else{
                sun = true
                btnsun.setBackgroundResource(R.drawable.app_bg_circle_stroked)
                btnsun.setTextColor(resources.getColor(R.color.white))
            }
        }
        else  if(week_click==1){
            if(mon){
                mon = false
                btnmon.setBackgroundResource(R.drawable.circle_with_white_color)
                btnmon.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            else{
                mon = true
                btnmon.setBackgroundResource(R.drawable.app_bg_circle_stroked)
                btnmon.setTextColor(resources.getColor(R.color.white))
            }
        }
        else  if(week_click==2){
            if(tue){
                tue = false
                btntue.setBackgroundResource(R.drawable.circle_with_white_color)
                btntue.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            else{
                tue = true
                btntue.setBackgroundResource(R.drawable.app_bg_circle_stroked)
                btntue.setTextColor(resources.getColor(R.color.white))
            }
        }
        else  if(week_click==3){
            if(wed){
                wed = false
                btnwed.setBackgroundResource(R.drawable.circle_with_white_color)
                btnwed.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            else{
                wed = true
                btnwed.setBackgroundResource(R.drawable.app_bg_circle_stroked)
                btnwed.setTextColor(resources.getColor(R.color.white))
            }
        }
        else  if(week_click==4){
            if(thu){
                thu = false
                btnthu.setBackgroundResource(R.drawable.circle_with_white_color)
                btnthu.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            else{
                thu = true
                btnthu.setBackgroundResource(R.drawable.app_bg_circle_stroked)
                btnthu.setTextColor(resources.getColor(R.color.white))
            }
        }
        else  if(week_click==5){
            if(fri){
                fri = false
                btnfri.setBackgroundResource(R.drawable.circle_with_white_color)
                btnfri.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            else{
                fri = true
                btnfri.setBackgroundResource(R.drawable.app_bg_circle_stroked)
                btnfri.setTextColor(resources.getColor(R.color.white))
            }

        }
        else  if(week_click==6){
            if(sat){
                sat = false
                btnsat.setBackgroundResource(R.drawable.circle_with_white_color)
                btnsat.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            else{
                sat = true
                btnsat.setBackgroundResource(R.drawable.app_bg_circle_stroked)
                btnsat.setTextColor(resources.getColor(R.color.white))
            }
        }
        else  if(week_click==7){
            if(days_24){
                days_24 = false
                btn_hours.setBackgroundResource(R.drawable.circle_with_white_color)
                btn_hours.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            else{
                days_24 = true
                btn_hours.setBackgroundResource(R.drawable.app_bg_circle_stroked)
                btn_hours.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    private fun init()
    {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        picker_year.setMinValue(1900)
        picker_year.setMaxValue(3500)
        picker_year.setValue(year)

        business_select_year_et.setText(""+year)

        var _24HourDt: Date? = null
        var currentHour = cal[Calendar.HOUR]
        var currentMinutes = cal[Calendar.MINUTE]
        Log.e("time"," "+currentHour+" "+currentMinutes)

        currentHour = 9
        currentMinutes = 0
        form_et.setText("$currentHour:$currentMinutes")
        _24HourDt = _24HourSDF.parse(form_et.getText().toString())
        form_et.setText(_12HourSDF.format(_24HourDt))

        currentHour = 22
        currentMinutes = 0
        to_et.setText("$currentHour:$currentMinutes")
        _24HourDt = _24HourSDF.parse(to_et.getText().toString())
        to_et.setText(_12HourSDF.format(_24HourDt))

        currentHour = 13
        currentMinutes = 0
        form_lunch_et.setText("$currentHour:$currentMinutes")
        _24HourDt = _24HourSDF.parse(form_lunch_et.getText().toString())
        form_lunch_et.setText(_12HourSDF.format(_24HourDt))

        currentHour = 14
        currentMinutes = 0
        to_lunch_et.setText("$currentHour:$currentMinutes")
        _24HourDt = _24HourSDF.parse(to_lunch_et.getText().toString())
        to_lunch_et.setText(_12HourSDF.format(_24HourDt))

        employee_click =0
        set_selected_empl()

        week_click = 0
        set_selected_week()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }

        next_business_img.setOnClickListener {
            validation()
        }

        form_et.setOnClickListener {
            time_click = 0
            open_time_picker()
        }
        to_et.setOnClickListener {
            time_click = 1
            open_time_picker()
        }
        form_lunch_et.setOnClickListener {
            time_click = 2
            open_time_picker()
        }
        to_lunch_et.setOnClickListener {
            time_click = 3
            open_time_picker()
        }
        time_cancel_txt.setOnClickListener {
            time_picker_rel.setVisibility(View.GONE)
        }
        time_okay_txt.setOnClickListener {
            time_picker_rel.setVisibility(View.GONE)
        }

        business_select_year_et.setOnClickListener {
            number_picker_rel.setVisibility(View.VISIBLE)
        }
        numberpicker_cancel_txt.setOnClickListener {
            number_picker_rel.setVisibility(View.GONE)
        }
        numberpicker_okay_txt.setOnClickListener {
            number_picker_rel.setVisibility(View.GONE)
        }

        picker_year.setOnValueChangedListener(OnValueChangeListener { numberPicker, i, i1 ->
            business_select_year_et.setText(""+numberPicker.value.toString())
        })
    }

    private fun open_time_picker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            time_picker_rel.setVisibility(View.VISIBLE)
            time_picker.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { view, hourOfDay, minute ->
                if(time_click==0)
                    form_et.setText("$hourOfDay:$minute")
                else  if(time_click==1)
                    to_et.setText("$hourOfDay:$minute")
                else  if(time_click==2)
                    form_lunch_et.setText("$hourOfDay:$minute")
                else
                    to_lunch_et.setText("$hourOfDay:$minute")

                var _24HourDt: Date? = null
                try {
                    if(time_click==0){
                        _24HourDt = _24HourSDF.parse(form_et.getText().toString())
                        form_et.setText(_12HourSDF.format(_24HourDt))
                    }
                    else if(time_click==1){
                        _24HourDt = _24HourSDF.parse(to_et.getText().toString())
                        to_et.setText(_12HourSDF.format(_24HourDt))
                    }
                    else if(time_click==2){
                        _24HourDt = _24HourSDF.parse(form_lunch_et.getText().toString())
                        form_lunch_et.setText(_12HourSDF.format(_24HourDt))
                    }
                    else{
                        _24HourDt = _24HourSDF.parse(to_lunch_et.getText().toString())
                        to_lunch_et.setText(_12HourSDF.format(_24HourDt))
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            })
        } else {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker = TimePickerDialog(this@BusinessDetailsActivity,
                TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->

                    if(time_click==0)
                        form_et.setText("$selectedHour:$minute")
                    else  if(time_click==1)
                        to_et.setText("$selectedHour:$minute")
                    else  if(time_click==2)
                        form_lunch_et.setText("$selectedHour:$minute")
                    else
                        to_lunch_et.setText("$selectedHour:$minute")

                    var _24HourDt: Date? = null
                    try {
                        if(time_click==0){
                            _24HourDt = _24HourSDF.parse(form_et.getText().toString())
                            form_et.setText(_12HourSDF.format(_24HourDt))
                        }
                        else if(time_click==1){
                            _24HourDt = _24HourSDF.parse(to_et.getText().toString())
                            to_et.setText(_12HourSDF.format(_24HourDt))
                        }
                        else if(time_click==2){
                            _24HourDt = _24HourSDF.parse(form_lunch_et.getText().toString())
                            form_lunch_et.setText(_12HourSDF.format(_24HourDt))
                        }
                        else{
                            _24HourDt = _24HourSDF.parse(to_lunch_et.getText().toString())
                            to_lunch_et.setText(_12HourSDF.format(_24HourDt))
                        }
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }, hour, minute, false
            )
            mTimePicker.show()
        }
    }

    private fun validation() {
        if(form_et.text.toString().isEmpty()){
            clear_error()
            from_layout_tl.error = resources.getString(R.string.from_error)
        }
        else if(to_et.text.toString().isEmpty()){
            clear_error()
            to_layout_tl.error = resources.getString(R.string.to_error)
        }
        else if(business_select_year_et.text.toString().isEmpty()){
            clear_error()
            select_year_t1.error = resources.getString(R.string.year_erroe)
        }
        else{
            api_calling_for_customer_business_detail()
        }
    }

    private fun clear_error() {
        from_layout_tl.error = null
        to_layout_tl.error = null
        select_year_t1.error = null
    }

    private fun api_calling_for_customer_business_detail()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        working_day_selection = ""
        if(sun){
            if(working_day_selection.isEmpty())
                working_day_selection = working_day_selection+"Sun"
            else
                working_day_selection = working_day_selection+",Sun"
        }
        if(mon){
            if(working_day_selection.isEmpty())
                working_day_selection = working_day_selection+"Mon"
            else
                working_day_selection =  working_day_selection+",Mon"
        }
        if(tue){
            if(working_day_selection.isEmpty())
                working_day_selection = working_day_selection+"Tue"
            else
                working_day_selection =  working_day_selection+",Tue"
        }
        if(wed){
            if(working_day_selection.isEmpty())
                working_day_selection = working_day_selection+"Wed"
            else
                working_day_selection =  working_day_selection+",Wed"
        }
        if(thu){
            if(working_day_selection.isEmpty())
                working_day_selection = working_day_selection+"Thu"
            else
                working_day_selection =  working_day_selection+",Thu"
        }
        if(fri){
            if(working_day_selection.isEmpty())
                working_day_selection = working_day_selection+"Fri"
            else
                working_day_selection =  working_day_selection+",Fri"
        }
        if(sat){
            if(working_day_selection.isEmpty())
                working_day_selection = working_day_selection+"Sat"
            else
                working_day_selection =  working_day_selection+",Sat"

        }
        if(days_24){
            if(working_day_selection.isEmpty())
                working_day_selection = working_day_selection+"24/7"
            else
                working_day_selection =  working_day_selection+",24/7"
        }

        ServiceCall.callCustomerBusinessDetail(this, auth_id, auth_token, Links.User_Type,
            working_day_selection, form_et.text.toString(),to_et.text.toString(),form_lunch_et.text.toString(),to_lunch_et.text.toString(),
            business_select_year_et.text.toString(), employee_selection,your_website_et.text.toString(),
            our_speciality_et.text.toString())
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success

                        if (success_v?.toInt()==1)
                        {
                            Links.snack_bar(this@BusinessDetailsActivity,main_layout,response.body()?.message.toString())
                            move_next_page()
                        } else {
                            Links.snack_bar(this@BusinessDetailsActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@BusinessDetailsActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@BusinessDetailsActivity,main_layout,t.message)
                    Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                }
            })
    }

    private fun move_next_page() {
        val intent = Intent(this, ServicesProvidersActivity::class.java)
        startActivity(intent)
    }


}
