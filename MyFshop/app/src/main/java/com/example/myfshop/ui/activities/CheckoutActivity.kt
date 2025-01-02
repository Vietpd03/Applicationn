//package com.example.myfshop.ui.activities
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.Parcel
//import android.os.Parcelable
//import android.view.View
//import android.widget.Button
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.myfshop.R
//import com.example.myfshop.firestore.FirestoreClass
//import com.example.myfshop.models.Address
//import com.example.myfshop.models.CartItem
//import com.example.myfshop.models.Order
//import com.example.myfshop.models.Product
//import com.example.myfshop.ui.adapters.CartItemsListAdapter
//import com.example.myfshop.utils.Constants
//
//
////class PaymentRequest(
////    val totalAmount: Double,
////    val currency: String,
////    val description: String,
////    val returnUrl: String
////) : Parcelable {
////
////    override fun describeContents(): Int {
////        return 0
////    }
////
////    override fun writeToParcel(dest: Parcel, flags: Int) {
////        dest.writeDouble(totalAmount)
////        dest.writeString(currency)
////        dest.writeString(description)
////        dest.writeString(returnUrl)
////    }
////
////    companion object CREATOR : Parcelable.Creator<PaymentRequest> {
////        override fun createFromParcel(parcel: Parcel): PaymentRequest? {
////            return PaymentRequest(
////                parcel.readDouble(),
////                parcel.readString() ?: "",
////                parcel.readString() ?: "",
////                parcel.readString() ?: ""
////            )
////        }
////
////        override fun newArray(size: Int): Array<PaymentRequest?> {
////            return arrayOfNulls(size)
////        }
////    }
////}
//
//
//class CheckoutActivity : BaseActivity() {
//
//    private var mAddressDetails: Address? = null
//    private lateinit var mProductsList: ArrayList<Product>
//    private lateinit var mCartItemsList: ArrayList<CartItem>
//    private var mSubTotal: Double = 0.0
//    private var mTotalAmount: Double = 0.0
//    private lateinit var mOrderDetails: Order
//    private lateinit var tvCheckoutTotalAmount: TextView
//    private lateinit var btnPlaceOrder: Button
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_checkout)
//
//        btnPlaceOrder = findViewById(R.id.btn_place_order)
//
//        btnPlaceOrder.setOnClickListener {
//            val totalAmount = 100.0 // Replace with your actual total amount
//            val intent = Intent(this, ZaloPayActivity::class.java)
//            intent.putExtra("totalAmount", totalAmount)
//            startActivity(intent)
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        setupActionBar()
//
//        if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
//            mAddressDetails = intent.getSerializableExtra(Constants.EXTRA_SELECTED_ADDRESS) as Address?
//        }
//
//        if (mAddressDetails != null) {
//            val tvCheckoutAddressType = findViewById<TextView>(R.id.tv_checkout_address_type)
//            val tvCheckoutFullName = findViewById<TextView>(R.id.tv_checkout_full_name)
//            val tvCheckoutAddress = findViewById<TextView>(R.id.tv_checkout_address)
//            val tvCheckoutAdditionalNote = findViewById<TextView>(R.id.tv_checkout_additional_note)
//            val tvCheckoutOtherDetails = findViewById<TextView>(R.id.tv_checkout_other_details)
//            val tvCheckoutMobileNumber = findViewById<TextView>(R.id.tv_checkout_mobile_number)
//            val tvCheckoutTotalAmount = findViewById<TextView>(R.id.tv_checkout_total_amount)
//            tvCheckoutAddressType.text = mAddressDetails?.type
//            tvCheckoutFullName.text = mAddressDetails?.name
//            tvCheckoutAddress.text = "${mAddressDetails!!.address}, ${mAddressDetails!!.zipCode}"
//            tvCheckoutAdditionalNote.text = mAddressDetails?.additionalNote
//            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
//                tvCheckoutOtherDetails.text = mAddressDetails?.otherDetails
//            }
//            tvCheckoutMobileNumber.text = mAddressDetails?.mobileNumber
//        }
//
//        getProductList()
//
//        val btnPlaceOrder = findViewById<TextView>(R.id.btn_place_order)
//        btnPlaceOrder.setOnClickListener {
//            handlePlaceOrder()
//        }
//    }
//
////    val paymentRequest = PaymentRequest(
////        totalAmount,
////        "USD",
////        "Test payment",
////        "https://example.com/return-url"
////    )
//
//    private fun handlePlaceOrder() {
//        // Validate total amount
//        val totalAmountText = tvCheckoutTotalAmount.text.toString().replace("$", "").trim()
//        val totalAmount = totalAmountText.toDoubleOrNull()
//
//        if (totalAmount == null || totalAmount <= 0) {
//            Toast.makeText(this, "Invalid total amount. Please try again.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Create a payment request
//        val paymentRequest = PaymentRequest(
//            totalAmount,
//            "USD",
//            "Test payment",
//            "https://example.com/return-url"
//        )
//
//        // Start the payment activity
//        val intent = Intent(this, ZaloPayActivity::class.java)
//        intent.putExtra("payment_request", paymentRequest)
//        startActivity(intent)
//    }
//
//
//
//
//    private fun setupActionBar() {
//        val toolbarCheckoutActivity = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_checkout_activity)
//        setSupportActionBar(toolbarCheckoutActivity)
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
//        }
//        toolbarCheckoutActivity.setNavigationOnClickListener { onBackPressed() }
//    }
//
//    private fun getProductList() {
//        showProgressDialog(resources.getString(R.string.please_wait))
//        FirestoreClass().getAllProductsList(this@CheckoutActivity)
//    }
//
//    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
//        mProductsList = productsList
//        getCartItemsList()
//    }
//
//    private fun getCartItemsList() {
//        FirestoreClass().getCartList(this@CheckoutActivity)
//    }
//
//    fun successCartItemsList(cartList: ArrayList<CartItem>) {
//        hideProgressDialog()
//
//        for (product in mProductsList) {
//            for (cart in cartList) {
//                if (product.product_id == cart.product_id) {
//                    cart.stock_quantity = product.stock_quantity
//                }
//            }
//        }
//
//        mCartItemsList = cartList
//
//        val rvCartListItems = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_cart_list_items)
//        rvCartListItems.layoutManager = LinearLayoutManager(this@CheckoutActivity)
//        rvCartListItems.setHasFixedSize(true)
//
//        val cartListAdapter = CartItemsListAdapter(this@CheckoutActivity, mCartItemsList, false)
//        rvCartListItems.adapter = cartListAdapter
//
//        val tvCheckoutSubTotal = findViewById<TextView>(R.id.tv_checkout_sub_total)
//        val tvCheckoutShippingCharge = findViewById<TextView>(R.id.tv_checkout_shipping_charge)
//        val llCheckoutPlaceOrder = findViewById<LinearLayout>(R.id.ll_checkout_place_order)
//        val tvCheckoutTotalAmount = findViewById<TextView>(R.id.tv_checkout_total_amount)
//
//        for (item in mCartItemsList) {
//            if (item.stock_quantity.isNotEmpty() && item.cart_quantity.isNotEmpty()) {
//                val availableQuantity = item.stock_quantity.toInt()
//                if (availableQuantity > 0) {
//                    val price = item.price.toDouble()
//                    val quantity = item.cart_quantity.toInt()
//                    mSubTotal += (price * quantity)
//                }
//            }
//        }
//
//        tvCheckoutSubTotal.text = "$$mSubTotal"
//        tvCheckoutShippingCharge.text = "$10.0"
//
//        if (mSubTotal > 0) {
//            llCheckoutPlaceOrder.visibility = View.VISIBLE
//            mTotalAmount = mSubTotal + 10.0
//            tvCheckoutTotalAmount.text = "$$mTotalAmount"
//        } else {
//            llCheckoutPlaceOrder.visibility = View.GONE
//        }
//    }
//
//    private fun placeAnOrder() {
//        showProgressDialog(resources.getString(R.string.please_wait))
//
//        val selectedSize = mCartItemsList[0].size
//
//        mOrderDetails = Order(
//            FirestoreClass().getCurrentUserID(),
//            mCartItemsList,
//            mAddressDetails!!,
//            "My order ${System.currentTimeMillis()}",
//            mCartItemsList[0].image,
//            mSubTotal.toString(),
//            "10.0",
//            mTotalAmount.toString(),
//            System.currentTimeMillis(),
//            size = selectedSize
//        )
//
//        FirestoreClass().placeOrder(this@CheckoutActivity, mOrderDetails)
//    }
//
//    fun orderPlacedSuccess() {
//        FirestoreClass().updateAllDetails(this@CheckoutActivity, mCartItemsList, mOrderDetails)
//    }
//
//    fun allDetailsUpdatedSuccessfully() {
//        hideProgressDialog()
//        Toast.makeText(this@CheckoutActivity, "Your order placed successfully.", Toast.LENGTH_SHORT).show()
//
//        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finish()
//    }
//}

package com.example.myfshop.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfshop.R
import com.example.myfshop.firestore.FirestoreClass
import com.example.myfshop.models.Address
import com.example.myfshop.models.CartItem
import com.example.myfshop.models.Order
import com.example.myfshop.models.Product
import com.example.myfshop.ui.adapters.CartItemsListAdapter
import com.example.myfshop.utils.Constants

class CheckoutActivity : BaseActivity() {

    private var mAddressDetails: Address? = null
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartItemsList: ArrayList<CartItem>
    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0
    private lateinit var mOrderDetails: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
            mAddressDetails = intent.getSerializableExtra(Constants.EXTRA_SELECTED_ADDRESS) as Address?
        }

        if (mAddressDetails != null) {
            val tvCheckoutAddressType = findViewById<TextView>(R.id.tv_checkout_address_type)
            val tvCheckoutFullName = findViewById<TextView>(R.id.tv_checkout_full_name)
            val tvCheckoutAddress = findViewById<TextView>(R.id.tv_checkout_address)
            val tvCheckoutAdditionalNote = findViewById<TextView>(R.id.tv_checkout_additional_note)
            val tvCheckoutOtherDetails = findViewById<TextView>(R.id.tv_checkout_other_details)
            val tvCheckoutMobileNumber = findViewById<TextView>(R.id.tv_checkout_mobile_number)

            tvCheckoutAddressType.text = mAddressDetails?.type
            tvCheckoutFullName.text = mAddressDetails?.name
            tvCheckoutAddress.text = "${mAddressDetails!!.address}, ${mAddressDetails!!.zipCode}"
            tvCheckoutAdditionalNote.text = mAddressDetails?.additionalNote
            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                tvCheckoutOtherDetails.text = mAddressDetails?.otherDetails
            }
            tvCheckoutMobileNumber.text = mAddressDetails?.mobileNumber
        }

        getProductList()

        val btnPlaceOrder = findViewById<TextView>(R.id.btn_place_order)
        btnPlaceOrder.setOnClickListener {
            //navigateToZaloPayActivity()
            placeAnOrder()
        }
    }

    private fun setupActionBar() {
        val toolbarCheckoutActivity = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_checkout_activity)
        setSupportActionBar(toolbarCheckoutActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        toolbarCheckoutActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getProductList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAllProductsList(this@CheckoutActivity)
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
        mProductsList = productsList
        getCartItemsList()
    }

    private fun getCartItemsList() {
        FirestoreClass().getCartList(this@CheckoutActivity)
    }

    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()

        for (product in mProductsList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stock_quantity
                }
            }
        }

        mCartItemsList = cartList

        val rvCartListItems = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_cart_list_items)
        rvCartListItems.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        rvCartListItems.setHasFixedSize(true)

        val cartListAdapter = CartItemsListAdapter(this@CheckoutActivity, mCartItemsList, false)
        rvCartListItems.adapter = cartListAdapter

        val tvCheckoutSubTotal = findViewById<TextView>(R.id.tv_checkout_sub_total)
        val tvCheckoutShippingCharge = findViewById<TextView>(R.id.tv_checkout_shipping_charge)
        val llCheckoutPlaceOrder = findViewById<LinearLayout>(R.id.ll_checkout_place_order)
        val tvCheckoutTotalAmount = findViewById<TextView>(R.id.tv_checkout_total_amount)

        for (item in mCartItemsList) {
            if (item.stock_quantity.isNotEmpty() && item.cart_quantity.isNotEmpty()) {
                val availableQuantity = item.stock_quantity.toInt()
                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()
                    mSubTotal += (price * quantity)
                }
            }
        }

        tvCheckoutSubTotal.text = "$$mSubTotal"
        tvCheckoutShippingCharge.text = "$10.0"

        if (mSubTotal > 0) {
            llCheckoutPlaceOrder.visibility = View.VISIBLE
            mTotalAmount = mSubTotal + 10.0
            tvCheckoutTotalAmount.text = "$$mTotalAmount"
        } else {
            llCheckoutPlaceOrder.visibility = View.GONE
        }
    }
//    private fun placeAnOrder() {
//        val intent = Intent(this@CheckoutActivity, ZaloPayActivity::class.java)
//        intent.putExtra("totalAmount", mTotalAmount) // Pass the total amount
//        startActivity(intent) // Navigate to ZaloPayActivity
//    }


    private fun placeAnOrder() {
        showProgressDialog(resources.getString(R.string.please_wait))

        val selectedSize = mCartItemsList[0].size

        mOrderDetails = Order(
            FirestoreClass().getCurrentUserID(),
            mCartItemsList,
            mAddressDetails!!,
            "My order ${System.currentTimeMillis()}",
            mCartItemsList[0].image,
            mSubTotal.toString(),
            "10.0",
            mTotalAmount.toString(),
            System.currentTimeMillis(),
            size = selectedSize
        )

        FirestoreClass().placeOrder(this@CheckoutActivity, mOrderDetails)
    }

    fun orderPlacedSuccess() {
        FirestoreClass().updateAllDetails(this@CheckoutActivity, mCartItemsList, mOrderDetails)
    }

    fun allDetailsUpdatedSuccessfully() {
        hideProgressDialog()
        Toast.makeText(this@CheckoutActivity, "Your order placed successfully.", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun navigateToZaloPayActivity() {
        val intent = Intent(this, ZaloPayActivity::class.java)
        intent.putExtra("totalAmount", mTotalAmount) // Pass the total amount
        startActivity(intent)
    }
}
