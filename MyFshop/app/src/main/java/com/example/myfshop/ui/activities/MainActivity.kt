package com.example.myfshop.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfshop.Constant.AppInfo.APP_ID
import com.example.myfshop.R
import com.example.myfshop.utils.Constants
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences =
            getSharedPreferences(Constants.MYFSHOP_PREFERENCES, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!

        val tv_main = findViewById<TextView>(R.id.tv_main)

        tv_main.text= "The logged in user is $username."

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // Khởi tạo ZaloPay SDK
        try {
            ZaloPaySDK.init(APP_ID, Environment.SANDBOX)
            Log.d("ZaloPayInit", "ZaloPay SDK initialized successfully")
        } catch (e: Exception) {
            Log.e("ZaloPayInitError", "Error initializing ZaloPay SDK: ${e.message}")
            e.printStackTrace()
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }


}