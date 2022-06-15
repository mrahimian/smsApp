package com.example.smsapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.mp.java.mpjava.SoapClient
import kotlinx.android.synthetic.main.activity_announce_price.*
import kotlinx.android.synthetic.main.activity_send_receive.*
import java.net.URI

class AnnouncePrice : AppCompatActivity() {
    private lateinit var mAdapter: AnnounceAdapter
    private lateinit var recyclerView: RecyclerView
//    private  var finalOrders: ArrayList<RequestInfo> = arrayListOf(RequestInfo("",""))
    val goods = MainActivity.currentOrder!!.goodsInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announce_price)



        recyclerView = findViewById(R.id.recyclerView)
        mAdapter = AnnounceAdapter(goods, context = this, recyclerView)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter



        button2.setOnClickListener {
            var count = goods.size
            goods.clear()
            for (i in 0 until count) {
                var view = recyclerView.getChildAt(i)
                val part: EditText = view.findViewById(R.id.kala)
                val price: EditText = view.findViewById(R.id.price)
                val name1 = part.text.toString()
                val name2 = price.text.toString()
                goods.add(RequestInfo(name1,name2))
            }
            
            Send(this).execute()
        }

        add_goods.setOnClickListener {
            var count = goods.size
            goods.clear()
            for (i in 0 until count) {
                Log.e(" "+mAdapter.itemCount,"items")
                var view = recyclerView.getChildAt(i)
                val part: EditText = view.findViewById(R.id.kala)
                val price: EditText = view.findViewById(R.id.price)
                val name1 = part.text.toString()
                val name2 = price.text.toString()
                goods.add(RequestInfo(name1,name2))
            }
            goods.add(RequestInfo("",""))
            mAdapter.notifyDataSetChanged()
        }
    }

    inner class Send(context: Context):AsyncTask<String,Void,String>(){
        val pd = ProgressDialog(context)
        override fun onPreExecute() {
            pd.setMessage("در حال ارسال پیامک ...")
            pd.show()
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            pd.dismiss()
            super.onPostExecute(result)
        }
        override fun doInBackground(vararg params: String?): String {
            sendSMS()
            return ""
        }

    }
    private fun sendSMS() {
        val from = "************"
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val soapClient = SoapClient("********", "*********")
            val client = MainActivity.currentOrder
            val obj: Any? =
                soapClient.SendSimpleSMS2(
                    client!!.clientPhone, from, TextsHandler.tellPrice(
                        client.gender, client.clientName , goods as ArrayList<RequestInfo> , TextsHandler.tellPrice
                    ), false
                )
            runOnUiThread {
                Toast.makeText(this,"پیامک ارسال شد",Toast.LENGTH_SHORT).show()
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}
