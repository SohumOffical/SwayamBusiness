package com.sngs.swayam.business.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sngs.swayam.business.R
import com.sngs.swayam.business.network.webUtlis.Links

import kotlinx.android.synthetic.main.activity_choose_role.*

class ChooseRoleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_choose_role)


        init()
        click_fun()

    }

    private fun init() {
        Links.selected_role = "Seller"
    }

    private fun click_fun() {

        back_img.setOnClickListener {
            finish()
        }

        seller_rel.setOnClickListener {
            ivGo.setBackgroundDrawable(resources.getDrawable(R.drawable.app_green_bg_circle_stroked))
            ivGo.setImageDrawable(resources.getDrawable(R.drawable.ic_check))

            ivGo_2.setBackgroundDrawable(resources.getDrawable(R.drawable.app_bg_circle_stroked))
            ivGo_2.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_right))

            ivGo_3.setBackgroundDrawable(resources.getDrawable(R.drawable.app_bg_circle_stroked))
            ivGo_3.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_right))
            Links.selected_role = "Seller"
        }

        service_rel.setOnClickListener {
            ivGo_2.setBackgroundDrawable(resources.getDrawable(R.drawable.app_green_bg_circle_stroked))
            ivGo_2.setImageDrawable(resources.getDrawable(R.drawable.ic_check))

            ivGo.setBackgroundDrawable(resources.getDrawable(R.drawable.app_bg_circle_stroked))
            ivGo.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_right))

            ivGo_3.setBackgroundDrawable(resources.getDrawable(R.drawable.app_bg_circle_stroked))
            ivGo_3.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_right))
            Links.selected_role = "Service Provider"
        }

        needs_rel.setOnClickListener {
            ivGo_3.setBackgroundDrawable(resources.getDrawable(R.drawable.app_green_bg_circle_stroked))
            ivGo_3.setImageDrawable(resources.getDrawable(R.drawable.ic_check))

            ivGo.setBackgroundDrawable(resources.getDrawable(R.drawable.app_bg_circle_stroked))
            ivGo.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_right))

            ivGo_2.setBackgroundDrawable(resources.getDrawable(R.drawable.app_bg_circle_stroked))
            ivGo_2.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_right))
        }

        next_img.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            intent.putExtra("page_type","1")
            startActivity(intent)
            finish()
        }
    }
}
