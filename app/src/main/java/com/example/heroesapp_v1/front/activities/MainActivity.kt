package com.example.heroesapp_v1.front.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.heroesapp_v1.front.utils.Constants
import com.example.heroesapp_v1.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_default_hero_img)
            .error(R.drawable.ic_error_hero_img)

        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load(Constants.splashImg).into(splash_image)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
