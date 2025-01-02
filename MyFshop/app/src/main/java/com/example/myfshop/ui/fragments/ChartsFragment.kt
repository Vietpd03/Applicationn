package com.example.myfshop.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myfshop.R
import com.example.myfshop.firestore.FirestoreClass
import com.example.myfshop.models.Order
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class ChartsFragment : Fragment() {

    private lateinit var chartRevenue: BarChart
    private lateinit var chartLine: LineChart
    private lateinit var chartPie: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_charts, container, false)

        // Initialize views
        chartRevenue = root.findViewById(R.id.chartRevenue)
        chartLine = root.findViewById(R.id.chartLine)
        chartPie = root.findViewById(R.id.chartPie)

        // Get Revenue Data and display charts
        getChartData()

        return root
    }

    private fun getChartData() {
        FirestoreClass().getAllOrders { ordersList ->
            // Bar Chart for Monthly Revenue
            val revenueData = calculateMonthlyRevenue(ordersList)
            setupBarChart(revenueData)

            // Line Chart for Order Growth
            val orderData = calculateOrderGrowth(ordersList)
            setupLineChart(orderData)

            // Pie Chart for Product Categories
            val pieData = calculateCategoryDistribution(ordersList)
            setupPieChart(pieData)
        }
    }

    private fun calculateMonthlyRevenue(ordersList: List<Order>): List<BarEntry> {
        val revenueByMonth = mutableMapOf<Int, Float>()
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())

        ordersList.forEach { order ->
            val month = dateFormat.format(Date(order.order_datetime)).substring(5, 7).toInt()
            revenueByMonth[month] = revenueByMonth.getOrDefault(month, 0f) + order.total_amount.toFloat()
        }

        return revenueByMonth.map { BarEntry(it.key.toFloat(), it.value) }
    }

    private fun calculateOrderGrowth(ordersList: List<Order>): List<Entry> {
        val orderByMonth = mutableMapOf<Int, Float>()
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())

        ordersList.forEach { order ->
            val month = dateFormat.format(Date(order.order_datetime)).substring(5, 7).toInt()
            orderByMonth[month] = orderByMonth.getOrDefault(month, 0f) + 1
        }

        return orderByMonth.map { Entry(it.key.toFloat(), it.value) }
    }

    private fun calculateCategoryDistribution(ordersList: List<Order>): List<PieEntry> {
        val categoryMap = mutableMapOf<String, Float>()

        ordersList.forEach { order ->
            val category = order.title // Assuming `title` represents the product category
            categoryMap[category] = categoryMap.getOrDefault(category, 0f) + order.total_amount.toFloat()
        }

        return categoryMap.map { PieEntry(it.value, it.key) }
    }

    private fun setupBarChart(revenueData: List<BarEntry>) {
        val dataSet = BarDataSet(revenueData, "Monthly Revenue")
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        val barData = BarData(dataSet)
        chartRevenue.data = barData

        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        chartRevenue.xAxis.valueFormatter = IndexAxisValueFormatter(months)
        chartRevenue.xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        chartRevenue.description.isEnabled = false
        chartRevenue.animateY(1000)
        chartRevenue.invalidate()
    }

    private fun setupLineChart(lineChartData: List<Entry>) {
        val lineDataSet = LineDataSet(lineChartData, "Order Growth")
        lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        lineDataSet.setDrawValues(false)

        val lineData = LineData(lineDataSet)
        chartLine.data = lineData
        chartLine.description.isEnabled = false
        chartLine.invalidate()
    }

    private fun setupPieChart(pieChartData: List<PieEntry>) {
        val pieDataSet = PieDataSet(pieChartData, "Product Categories")
        pieDataSet.colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.colorPrimary),
            ContextCompat.getColor(requireContext(), R.color.blue),
            ContextCompat.getColor(requireContext(), R.color.colorOrderStatusDelivered),
            ContextCompat.getColor(requireContext(), R.color.colorOrderStatusInProcess)
        )

        val pieData = PieData(pieDataSet)
        chartPie.data = pieData
        chartPie.description.isEnabled = false
        chartPie.invalidate()
    }
}
