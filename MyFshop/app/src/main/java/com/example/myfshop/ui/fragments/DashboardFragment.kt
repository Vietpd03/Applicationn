package com.example.myfshop.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfshop.R
import com.example.myfshop.firestore.FirestoreClass
import com.example.myfshop.models.Product
import com.example.myfshop.ui.activities.CartListActivity
import com.example.myfshop.ui.activities.ProductDetailsActivity
import com.example.myfshop.ui.activities.SettingsActivity
import com.example.myfshop.ui.adapters.DashboardItemsListAdapter
import com.example.myfshop.ui.adapters.MyProductsListAdapter
import com.example.myfshop.utils.Constants
import com.example.myfshop.utils.MSPButton

class DashboardFragment : BaseFragment() {
    private lateinit var mRootView: View
    private lateinit var productListAdapter: DashboardItemsListAdapter
    private var productList: ArrayList<Product> = ArrayList()
    private var filteredList: ArrayList<Product> = ArrayList()
    private lateinit var searchView: SearchView
    private lateinit var searchButton: MSPButton
    //private lateinit var productListAdapter: MyProductsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Initialize mRootView here
//        mRootView = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        return mRootView
//    }
override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    mRootView = inflater.inflate(R.layout.fragment_dashboard, container, false)

    // Initialize SearchView and Button
    searchView = mRootView.findViewById(R.id.search_view)
    searchButton = mRootView.findViewById(R.id.btn_search)

    searchButton.setOnClickListener {
        filterProducts(searchView.query.toString())
    }

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            filterProducts(query ?: "")
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            filterProducts(newText ?: "")
            return true
        }
    })

    return mRootView
}



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            R.id.action_cart -> {
                startActivity(Intent(activity, CartListActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {
//
//        val rvdashboarditems = mRootView.findViewById<RecyclerView>(R.id.rv_dashboard_items)
//        val tvnodashboarditemsfound = mRootView.findViewById<TextView>(R.id.tv_no_dashboard_items_found)
//        // Hide the progress dialog.
//        hideProgressDialog()
//
//        if (dashboardItemsList.size > 0) {
//
//            rvdashboarditems.visibility = View.VISIBLE
//            tvnodashboarditemsfound.visibility = View.GONE
//
//            rvdashboarditems.layoutManager = GridLayoutManager(activity, 2)
//            rvdashboarditems.setHasFixedSize(true)
//
//            val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
//            rvdashboarditems.adapter = adapter
//
//            adapter.setOnClickListener(object :
//                DashboardItemsListAdapter.OnClickListener {
//                override fun onClick(position: Int, product: Product) {
//                    val intent = Intent(context, ProductDetailsActivity::class.java)
//                    intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
//                    startActivity(intent)
//                }
//            })
//        } else {
//            rvdashboarditems.visibility = View.GONE
//            tvnodashboarditemsfound.visibility = View.VISIBLE
//        }
//    }

    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {

        productList = dashboardItemsList
        filteredList = ArrayList(productList)
        hideProgressDialog()
        updateRecyclerView()
    }


    private fun getDashboardItemsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getDashboardItemsList(this@DashboardFragment)
    }

    override fun onResume() {
        super.onResume()

        getDashboardItemsList()
    }

    fun filterProducts(query: String) {
        filteredList = if (query.isEmpty()) {
            ArrayList(productList)
        } else {
            val filtered = ArrayList<Product>()
            for (product in productList) {
                if (product.title.contains(query, ignoreCase = true)) {
                    filtered.add(product)
                }
            }
            filtered
        }

        updateRecyclerView()
    }


    private fun updateRecyclerView() {
        val rvdashboarditems = mRootView.findViewById<RecyclerView>(R.id.rv_dashboard_items)
        val tvnodashboarditemsfound = mRootView.findViewById<TextView>(R.id.tv_no_dashboard_items_found)

        // Check if the views are properly initialized
        if (rvdashboarditems == null || tvnodashboarditemsfound == null) {
            Log.e("DashboardFragment", "RecyclerView or TextView is null")
            return
        }

        if (filteredList.isNotEmpty()) {
            rvdashboarditems.visibility = View.VISIBLE
            tvnodashboarditemsfound.visibility = View.GONE

            rvdashboarditems.layoutManager = GridLayoutManager(activity, 2)
            rvdashboarditems.setHasFixedSize(true)

            productListAdapter = DashboardItemsListAdapter(requireActivity(), filteredList)
            rvdashboarditems.adapter = productListAdapter

            productListAdapter.setOnClickListener(object :
                DashboardItemsListAdapter.OnClickListener {
                override fun onClick(position: Int, product: Product) {
                    val intent = Intent(context, ProductDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
                    startActivity(intent)
                }
            })
        } else {
            rvdashboarditems.visibility = View.GONE
            tvnodashboarditemsfound.visibility = View.VISIBLE
        }
    }

}
