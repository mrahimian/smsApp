package com.example.smsapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.mp.java.mpjava.SoapClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_send_receive.*

class MainActivity : AppCompatActivity() {
    companion object {
        var currentId: String = ""
        var currentOrder: OrdersInfo? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        new_order.setOnClickListener {
            startActivity(Intent(this, SubmitOrder::class.java))
            finish()
        }

//        setData(this,10).execute()

        var spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, StartActivity.ids)
        id_spinner.adapter = spinnerAdapter
//        spinnerAdapter.notifyDataSetChanged()

        id_spinner.setSelection(0)


        id_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setData(this@MainActivity,10).execute()
            }

        }







    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.tell_price_sms -> {
                startActivity(Intent(this, AnnouncePrice::class.java))
                true
            }
            R.id.send_receive_sms -> {
                startActivity(Intent(this, SendReceive::class.java))
                true
            }
            R.id.take_to_freight -> {
                startActivity(Intent(this, Freight::class.java))
                true
            }
            R.id.final_order -> {
                setData(this,0).execute()
                true
            }
            R.id.take_to_transport_unit -> {
                setData(this,1).execute()
                true
            }
            R.id.manage_texts -> {
                startActivity(Intent(this, ManageTexts::class.java))
                true
            }
            R.id.send_payment_link -> {
                startActivity(Intent(this, SendLink::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendSMS(num: Int) {
        val from = "************"
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val soapClient = SoapClient("*******", "**********")
            val client = currentOrder
            if (num == 0) {
                soapClient.SendSimpleSMS2(
                    client!!.clientPhone, from, TextsHandler.finalOrder(
                        client.gender,
                        client.clientName,
                        TextsHandler.finalOrder
                    ), false
                )
            } else if (num == 1) {
                soapClient.SendSimpleSMS2(
                    client!!.clientPhone, from, TextsHandler.takeToTransport(
                        client.gender,
                        client.clientName,
                        TextsHandler.takeToTransport
                    ), false
                )
            }
            runOnUiThread {
                Toast.makeText(this,"پیامک ارسال شد",Toast.LENGTH_SHORT).show()
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    inner class setData(val context: Context , val task : Int) : AsyncTask<String, Void, String>() {
        private val pd = ProgressDialog(context)
        override fun onPreExecute() {
            if (task == 10) {
            }else{
                pd.setMessage("در حال ارسال پیامک ...")
                pd.show()
            }
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            pd.dismiss()

            super.onPostExecute(result)
        }

        override fun doInBackground(vararg params: String?): String {
            if (task == 10) {
                for (item in StartActivity.orders) {
                    if (item.order_id == id_spinner.selectedItem.toString()) {
                        currentOrder = item
                    }
                }
                runOnUiThread {
                    if (currentOrder != null) {
                        announce_gender.text = currentOrder!!.gender
                    }
                    if (currentOrder != null) {
                        announce_name.text = currentOrder!!.clientName
                    }
                    if (currentOrder != null) {
                        announce_phone.text = currentOrder!!.clientPhone
                        announce_phone.setOnClickListener {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:${currentOrder!!.clientPhone}")
                            startActivity(intent)
                        }
                    }
                }
            }else {
                sendSMS(task)
            }
            return ""
        }

    }

}
