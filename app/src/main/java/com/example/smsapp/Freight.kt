package com.example.smsapp

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import ir.mp.java.mpjava.SoapClient
import kotlinx.android.synthetic.main.activity_freight.*
import kotlinx.android.synthetic.main.activity_send_receive.*

class Freight : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freight)

        freight_button.setOnClickListener {
            Send(this).execute()
        }
    }

    inner class Send(context: Context): AsyncTask<String, Void, String>(){
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
        val from = "******"
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
//                var p = arrayOf("*******","*******")
            val soapClient = SoapClient("*******", "*********")
            val client = MainActivity.currentOrder
            val obj: Any? =
                soapClient.SendSimpleSMS2(
                    client!!.clientPhone, from, TextsHandler.takeToFreight(
                        client.gender,
                        client.clientName,
                        freight_name.text.toString(),
                        recid_id.text.toString(),
                        freight_num.text.toString(),
                        TextsHandler.takeToFreight
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
