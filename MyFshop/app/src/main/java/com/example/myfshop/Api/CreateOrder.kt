//package com.example.myfshop.api
//
//import com.example.myfshop.Api.HttpProvider
//import com.example.myfshop.Constant.AppInfo
//import com.example.myfshop.Helper.Helpers
//import com.example.myfshop.models.ZaloPayOrder
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import okhttp3.FormBody
//import okhttp3.RequestBody
//import org.json.JSONObject
//import java.util.*
//
//class CreateOrder(private val amount: String) {
//
//    private class CreateOrderData(amount: String) {
//        val AppId: String
//        val AppUser: String
//        val AppTime: String
//        val Amount: String
//        val AppTransId: String
//        val EmbedData: String
//        val Items: String
//        val BankCode: String
//        val Description: String
//        val Mac: String
//
//        init {
//            val appTime = Date().time
//            AppId = AppInfo.APP_ID.toString()
//            AppUser = "Android_Demo"
//            AppTime = appTime.toString()
//            Amount = amount
//            AppTransId = Helpers.getAppTransId()
//            EmbedData = "{}"
//            Items = "[]"
//            BankCode = "zalopayapp"
//            Description = "Merchant pay for order #${Helpers.getAppTransId()}"
//            val inputHMac = "${this.AppId}|${this.AppTransId}|${this.AppUser}|${this.Amount}|${this.AppTime}|${this.EmbedData}|${this.Items}"
//            Mac = Helpers.getMac(AppInfo.MAC_KEY, inputHMac)
//        }
//    }
//
//    @Throws(Exception::class)
//    suspend fun createOrder(): JSONObject? {
//        val input = CreateOrderData(amount)
//        val formBody = FormBody.Builder()
//            .add("app_id", input.AppId)
//            .add("app_user", input.AppUser)
//            .add("app_time", input.AppTime)
//            .add("amount", input.Amount)
//            .add("app_trans_id", input.AppTransId)
//            .add("embed_data", input.EmbedData)
//            .add("item", input.Items)
//            .add("bank_code", input.BankCode)
//            .add("description", input.Description)
//            .add("mac", input.Mac)
//            .build()
//
//        return HttpProvider.sendPost(AppInfo.URL_CREATE_ORDER, formBody)
//    }
//}

//class CreateOrder {
//
//    @Throws(Exception::class)
//    suspend fun createOrder(amount: String): JSONObject {
//        return withContext(Dispatchers.IO) {
//            val input = ZaloPayOrder(amount = amount)
//            val formBody: RequestBody = FormBody.Builder()
//                .add("app_id", input.appId)
//                .add("app_user", input.appUser)
//                .add("app_time", input.appTime)
//                .add("amount", input.amount)
//                .add("app_trans_id", input.appTransId)
//                .add("embed_data", input.embedData)
//                .add("item", input.items)
//                .add("bank_code", input.bankCode)
//                .add("description", input.description)
//                .add("mac", input.mac)
//                .build()
//
//            HttpProvider.sendPost(url = AppInfo.URL_CREATE_ORDER, formBody =  formBody)
//        } ?: throw Exception("Failed to create order")
//    }
//}
package com.example.myfshop.api

import com.example.myfshop.Api.HttpProvider
import com.example.myfshop.Constant.AppInfo
import com.example.myfshop.Helper.Helpers
import com.example.myfshop.models.ZaloPayOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.RequestBody
import org.json.JSONObject

//class CreateOrder {
//
//    // Hàm tạo đơn hàng với số tiền được chỉ định
//    @Throws(Exception::class)
//    suspend fun createOrder(amount: String): JSONObject {
//        return withContext(Dispatchers.IO) {
//            val appTransId = Helpers.getAppTransId() // Lấy mã giao dịch duy nhất
//            val appTime = System.currentTimeMillis().toString() // Thời gian hiện tại
//            val embedData = "{}" // Dữ liệu nhúng (có thể mở rộng)
//            val items = "[]" // Danh sách sản phẩm (để trống hoặc thêm JSON)
//            val bankCode = "zalopayapp" // Mã ngân hàng
//            val description = "Merchant pay for order #$appTransId" // Mô tả giao dịch
//
//            // Chuỗi cần mã hóa
//            val macInput = "${AppInfo.APP_ID}|$appTransId|Android_Demo|$amount|$appTime|$embedData|$items"
//            val mac = Helpers.getMac(AppInfo.MAC_KEY, macInput) // Tạo MAC từ chuỗi
//
//            // Tạo request body
//            val formBody: RequestBody = FormBody.Builder()
//                .add("app_id", AppInfo.APP_ID.toString())
//                .add("app_user", "Android_Demo")
//                .add("app_time", appTime)
//                .add("amount", amount)
//                .add("app_trans_id", appTransId)
//                .add("embed_data", embedData)
//                .add("item", items)
//                .add("bank_code", bankCode)
//                .add("description", description)
//                .add("mac", mac)
//                .build()
//
//            // Gửi yêu cầu HTTP POST
//            HttpProvider.sendPost(AppInfo.URL_CREATE_ORDER, formBody)
//                ?: throw Exception("Failed to create order") // Ném lỗi nếu response null
//        }
//    }
//}
class CreateOrder {

    @Throws(Exception::class)
    suspend fun createOrder(amount: String): JSONObject {
        return withContext(Dispatchers.IO) {
            // Tạo đối tượng ZaloPayOrder với các giá trị cần thiết
            val input = ZaloPayOrder(amount = amount)

            // Tạo chuỗi MAC
            val macInput = "${AppInfo.APP_ID}|${input.appTransId}|${input.appUser}|${input.amount}|${input.appTime}|${input.embedData}|${input.items}"
            val mac = Helpers.getMac(AppInfo.MAC_KEY, macInput) // Tạo MAC từ chuỗi

            // Tạo request body
            val formBody: RequestBody = FormBody.Builder()
                .add("app_id", input.appId)
                .add("app_user", input.appUser)
                .add("app_time", input.appTime)
                .add("amount", input.amount)
                .add("app_trans_id", input.appTransId)
                .add("embed_data", input.embedData)
                .add("item", input.items)
                .add("bank_code", input.bankCode)
                .add("description", input.description)
                .add("mac", mac) // Thêm MAC vào request body
                .build()

            // Gửi yêu cầu HTTP POST
            HttpProvider.sendPost(url = AppInfo.URL_CREATE_ORDER, formBody = formBody)
                ?: throw Exception("Failed to create order")
        }
    }
}