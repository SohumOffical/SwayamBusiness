package com.sngs.swayam.business.activity.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.sngs.swayam.business.users.LoginActivity
import com.naimee.swayam.utlis.theme3bottomnavigation.BottomNavigation
import com.sngs.swayam.business.R
import com.sngs.swayam.business.activity.mytractions.MyTractionsActivity
import com.sngs.swayam.business.activity.notifications.NotificationActivity
import com.sngs.swayam.business.activity.onboarding.promos.PromotionDraftActivity
import com.sngs.swayam.business.activity.onboarding.promos.PromotionManagementActivity
import com.sngs.swayam.business.activity.packages.ListPackagesActivity
import com.sngs.swayam.business.activity.profile.EditProfileActivity
import com.sngs.swayam.business.activity.promotion.PromoteYourselfActivity
import com.sngs.swayam.business.activity.redeemcoin.RedeemCoinActivity
import com.sngs.swayam.business.network.webUtlis.Links
import com.sngs.swayam.business.network.model.CustomerDetail.CustomerDetailBaseResponse
import com.sngs.swayam.business.network.model.ServiceProvider.GetServiceProviderBaseResponse
import com.sngs.swayam.business.network.servicecall.ServiceCall
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.bottomNavigation
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val ID_HOME = 1
        private const val ID_MESSAGE = 3
        private const val ID_NOTIFICATION = 4
        private const val ID_ACCOUNT = 5
    }
    var speedScroll : Long = 10000;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_drawer)

        init()
        set_drawer()
        click_drawer()
        auto_swipe_promotion()
    }

    private fun click_drawer() {
        logout_li1.setOnClickListener {
            logout()
        }

        profile_li.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        promote_yourself_li1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            if(Links.is_Promissing_visiblity){
                val intent = Intent(this, PromoteYourselfActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this, ListPackagesActivity::class.java)
                startActivity(intent)
            }
        }

        promotions_li1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, PromotionManagementActivity::class.java)
            startActivity(intent)
        }


        promote_draft_li.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, PromotionDraftActivity::class.java)
            startActivity(intent)
        }

        promote_yourself_li.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            if(Links.is_Promissing_visiblity){
                val intent = Intent(this, PromoteYourselfActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this, ListPackagesActivity::class.java)
                startActivity(intent)
            }
        }

        my_transactions_txt1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, MyTractionsActivity::class.java)
            startActivity(intent)
        }

        redeem_coin_li1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, RedeemCoinActivity::class.java)
            startActivity(intent)
        }

        swayam_package_li1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, ListPackagesActivity::class.java)
            startActivity(intent)
        }

        contact_us_li1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, ContactusActivity::class.java)
            startActivity(intent)
        }

        about_us_li1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }

        active_promotion1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            move_next_page(1)
        }

        total_view_li.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, PromotionManagementActivity::class.java)
            startActivity(intent)
        }

    }

    private fun init() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("User_name","")
        val mobile_no = sharedPreferences.getString("User_no","")
        user_name_txt.setText(""+name)
        number_txt.setText(""+mobile_no)


        bottomNavigation.add(BottomNavigation.Model(
            ID_HOME,
            R.drawable.ic_home
        ))
        bottomNavigation.add(BottomNavigation.Model(
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

            if(name.equals("ACCOUNT")){
                move_next_page(3)
            }


        }
        Handler().postDelayed(Runnable {
            bottomNavigation.show(ID_HOME,true)

        },800)

        api_calling_for_customer_detail()
        api_calling_for_service_provide_detail()
    }

    fun set_drawer() {

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerlayout,
            R.string.open,
            R.string.close
        ) {
            private val scaleFactor = 10f
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                content.translationX = slideX
                content.scaleX = 1 - slideOffset / scaleFactor
                content.scaleY = 1 - slideOffset / scaleFactor
            }
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                btnMenu.setImageResource(R.drawable.ic_drawer)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                btnMenu.setImageResource(R.drawable.ic_back_arrow)
            }
        }

        drawerlayout.setScrimColor(Color.TRANSPARENT)
        drawerlayout.drawerElevation = 0f
        drawerlayout.addDrawerListener(actionBarDrawerToggle)

        btnMenu.setOnClickListener {
            drawerlayout.openDrawer(GravityCompat.START);
            setStatusBarGradient()
        }
    }

    private fun api_calling_for_customer_detail()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

       // loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callCustomerDetail(this, auth_id , auth_token , Links.User_Type,auth_id)
            .enqueue(object : Callback<CustomerDetailBaseResponse> {
                override fun onResponse(call: Call<CustomerDetailBaseResponse>, response: Response<CustomerDetailBaseResponse>) {
                 //   loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            set_data(response)
                        }
                        else {
                         //   Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                        }
                    } else {
                       // Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<CustomerDetailBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@HomeActivity,content,t.message.toString())
                }
            })
    }

    private fun set_data(response: Response<CustomerDetailBaseResponse>) {

       /* Links.Banner_list.clear()

        if(!response.body()!!.customerResult.customerMedia1.isEmpty())
            Links.Banner_list.add(""+ response.body()!!.customerResult.customerMedia1)

        if(!response.body()!!.customerResult.customerMedia2.isEmpty())
            Links.Banner_list.add(""+ response.body()!!.customerResult.customerMedia2)

        if(!response.body()!!.customerResult.customerMedia3.isEmpty())
            Links.Banner_list.add(""+ response.body()!!.customerResult.customerMedia3)

        if(!response.body()!!.customerResult.customerMedia4.isEmpty())
            Links.Banner_list.add(""+ response.body()!!.customerResult.customerMedia4)*/


        no_smart_user_txt.setText(""+ response.body()!!.getmTotalUser())
        user_name_txt.setText(""+ response.body()!!.customerResult.customerName)
        tv_user_name.setText(""+ response.body()!!.customerResult.customerName)
        tv_user_mobile.setText(""+ response.body()!!.customerResult.customerContactNumber)
        address_txt.setText(""+response.body()!!.customerResult.customerShopArea)
        city_txt.setText(""+response.body()!!.customerResult.customerShopCity)
        number_txt.setText(""+ response.body()!!.customerResult.customerContactNumber)
        coins.setText(""+ response.body()!!.customerResult.getmCustomerAvailableCoins())
        no_active_promation_txt.setText(""+ response.body()!!.customerResult.getmTotalPromotion())
        no_last_days_txt.setText(""+ response.body()!!.customerResult.getmTotalViewPromotion())

        tv_user_shop_code.setText(""+ response.body()!!.customerResult.getmCustomerNumber())

        Glide.with(applicationContext).load(response.body()!!.customerResult!!.getmCustomerProfilePicture())
                .placeholder(R.drawable.ic_new_user).into(profile_nav_img);

        Glide.with(applicationContext).load(response.body()!!.customerResult!!.getmCustomerProfilePicture())
                .placeholder(R.drawable.ic_new_user).into(profile_img);

        if(response.body()!!.customerResult.getmNumberOfCustomer().toInt()>0){
            Links.is_Promissing_visiblity = true
        }
        else{
            Links.is_Promissing_visiblity = false
        }

    /*    val adapter = WalkAdapter()
        viewpager.adapter = adapter
        dots.setViewPager(viewpager)
        viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })*/

    }

    fun setStatusBarGradient(color: Int = R.color.white) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val window = window
                var flags = window.decorView.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.decorView.systemUiVisibility = flags
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(this, color)
            }
            else -> {
                window.statusBarColor =  ContextCompat.getColor(this,
                    R.color.view_color
                )
            }
        }
    }

    internal inner class WalkAdapter : PagerAdapter() {
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context).inflate(R.layout.layout_viewpager, container, false)

            Glide.with(applicationContext).load(Links.Banner_list.get(position)).into((view.findViewById(R.id.img_banner) as ImageView));

            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return  Links.Banner_list.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as View
        }
    }

    private fun move_next_page(page_no : Int) {
        if(page_no==1){
            val intent = Intent(this, PromotionManagementActivity::class.java)
            startActivity(intent)
        }
        else  if(page_no==2){
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        else  if(page_no==3){
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        api_calling_for_customer_detail()
        bottomNavigation.show(ID_HOME,true)
    }

    private  fun logout()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("Is_Login","false")
        editor.commit()
        finish()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(Gravity.LEFT)
        } else {
            back_dialog_box()
        }
    }

    private fun back_dialog_box(){
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(R.string.app_name)

        //set message for alert dialog
        builder.setMessage(R.string.back_mes)
        builder.setIcon(R.drawable.app_logo)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            finish()
        }

        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->

        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    private fun api_calling_for_service_provide_detail()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam App", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        ServiceCall.callGetServiceProviderList(this, auth_id, auth_token, Links.User_Type,"1")
            .enqueue(object : Callback<GetServiceProviderBaseResponse> {
                override fun onResponse(call: Call<GetServiceProviderBaseResponse>, response: Response<GetServiceProviderBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.service_list.clear()
                        Links.service_sp_list.clear()

                        if (success_v?.toInt()==1) {
                            Links.service_list = response.body()!!.serviceListData
                            for (i in 0..(Links.service_list.size-1)) {
                                    Links.service_sp_list.add(Links.service_list.get(i).serviceName)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<GetServiceProviderBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                }
            })
    }

    private fun auto_swipe_promotion()
    {
        var  handler_banner = Handler();
        val runnable = object : Runnable {
            override fun run() {
                handler_banner.postDelayed(this, speedScroll);
                api_calling_for_customer_detail()
            }
        }
        handler_banner.postDelayed(runnable, speedScroll);
    }



}
