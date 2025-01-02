package com.example.myfshop.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfshop.Constant.AppInfo.APP_ID
import com.example.myfshop.R
import com.example.myfshop.utils.MSPButton
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.Environment
import java.io.IOException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class ZaloPayActivity : AppCompatActivity() {

    private lateinit var tvZaloPayAmount: TextView
    private lateinit var btnConfirmPayment: MSPButton

    private var totalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zalo_pay)

        // Thiết lập Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_zalo_pay_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true) // Hiển thị nút quay lại
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp) // Icon nút quay lại
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed() // Xử lý quay lại khi nhấn nút
        }

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)

        // Bind Views
        tvZaloPayAmount = findViewById(R.id.tv_zalo_pay_amount)
        btnConfirmPayment = findViewById(R.id.btn_confirm_payment)

        // Lấy total amount từ CheckoutActivity
        totalAmount = intent.getDoubleExtra("totalAmount", 0.0)

        // Hiển thị tổng số tiền
        tvZaloPayAmount.text = "Amount: $%.2f".format(totalAmount)

        // Xử lý khi nhấn Confirm Payment
        btnConfirmPayment.setOnClickListener {
            handlePaymentWithZaloPay()
        }
    }

    private fun handlePaymentWithZaloPay() {
        // Implement your payment logic with ZaloPay here
        val token = "YOUR_ZALOPAY_TOKEN" // Thay bằng token thực tế
        ZaloPaySDK.getInstance().payOrder(
            this@ZaloPayActivity,
            token,
            "demozpdk://app",  // ZaloPay return URL
            object : PayOrderListener {
                override fun onPaymentSucceeded(transactionId: String, transToken: String, appTransID: String) {
                    runOnUiThread {
                        // Xử lý khi thanh toán thành công
                        Toast.makeText(applicationContext, "Payment Succeeded: $transactionId", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPaymentCanceled(zpTransToken: String, appTransID: String) {
                    runOnUiThread {
                        // Xử lý khi thanh toán bị hủy
                        Toast.makeText(applicationContext, "Payment Canceled: $zpTransToken", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPaymentError(zaloPayError: ZaloPayError, zpTransToken: String, appTransID: String) {
                    runOnUiThread {
                        // Xử lý khi có lỗi thanh toán
                        Toast.makeText(applicationContext, "Payment Error: ${zaloPayError.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}
