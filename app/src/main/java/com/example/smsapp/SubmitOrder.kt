package com.example.smsapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.mp.java.mpjava.SoapClient
import kotlinx.android.synthetic.main.activity_submit_order.*
import kotlinx.android.synthetic.main.each_item.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.system.exitProcess

class SubmitOrder : AppCompatActivity() {
    private lateinit var mAdapter: OrderAdapter
    private lateinit var recyclerView: RecyclerView
    private val goods: MutableList<String> = mutableListOf("")
    private lateinit var cars : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_order)

        gender.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("  آقا  ", "  خانم  ")
        )

        recyclerView = findViewById(R.id.recycler_view_submit_order)
        mAdapter = OrderAdapter(goods, context = this, recyclerView)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter

        //cars
        cars = arrayOf("ام جی ZS","ام جی RX5","ام جی GT","ام جی GS","ام جی 6","ام جی 550","ام جی 360","ام جی 350","ام جی 3","کراس","ام وی ام V5","ام وی ام X55","ام وی ام X33S","ام وی ام X33","ام وی امX22","ام وی ام 550","ام وی ام 530","ام وی ام 315 هاچ بک","ام وی ام 315 صندوق دار","ام وی ام 110 S","ام وی ام 110","میتو","جولیتا","آلفتا","آلفارومئو 4C","H330","H320","H2L","H230","H220","نام خودرو","ون بنز","بنز کلاس SLR","بنز کلاس SLS","بنز کلاس SLK","بنز کلاس SLC","بنز کلاس SL","بنز کلاس SE","بنز کلاس S کوپه","بنز کلاس S","بنز کلاس ML","بنز کلاس GLK","بنز کلاس GLE","بنز کلاس GLC","بنز کلاس GLA","بنز کلاس GL","بنز کلاس G","بنز کلاس E کوپه","بنز کلاس E کروک","بنز کلاس E","بنز کلاس CLS","بنز کلاس CLK کوپه","بنز کلاس CLK کروک","بنز کلاس CLA","بنز کلاس CL","بنز کلاس CE","بنز کلاس C کوپه","بنز کلاس C","بنز کلاس B","بنز کلاس A","بنز GTS","بنز GL550","بی ام و سری M","بی ام و سری i","بی ام و سری 8","بی ام و سری 7","بی ام و سری 6 گرن کوپه","بی ام و سری 6 کوپه","بی ام و سری6 کروک","بی ام و سری 5 سدان","سری 5 GT","سری 4 گرن کوپه","بی ام و سری 4 کوپه","بی ام و سری 4 کروک","بی ام و سری 3 کروک","بی ام و سری 3 کوپه","بی ام و سری 3 سدان","بی ام و سری 3 GT","بی ام و سری 2 کوپه","بی ام و سری 2 کروک","بی ام و سری 2 اکتیوتورر","بی ام و سری 1 هاچ بک","بی ام و سری 1 کوپه","بی ام و سری 1 کروک","بی ام و Z4","بی ام و Z3","بی ام و X6","بی ام و X5","بی ام و X4","بی ام و X1","بی ام و X3","بی وای دی S7","بی وای دی S6","بی وای دی F3","پاژن دودر","پاژن چهاردر","پراید151","141","132","پراید131","پراید111","ویرا","جن تو","ایمپین","پژوRD","روآ","پژو پارس","پژو504","پژو407","پژو405","پژو301","پژو207","پژو206 SD","پژو206","پژو205","پژو204","پژو2008","ماکان","کیمن s","کاین","پانامرا","باکستر","پورشه 918","پورشه 911","وانت","سدان","تیبا هاچ بک","تیبا صندوق دار","یاریس","هایلوکس","هایس","ون تویوتا","لندکروز","کمری هیبرید","کمری","کریسیدا","کرولا","سکویا","تویوتا RAV 4","پریوس","پرادو","اف جی کروز","اریون","تیوتا C-HR هیبرید","تیوتا GT 86","گرند","ریفاین","جک جی 5","جک جی 4","جک جی 3 هاچ بک","جک جی 3 سدان","اسپرت","جک S5","جک S3","دنده ای","امگرند X7","امگرند RV-7","امگرند 8","امگرند 7","اتوماتیک","جیلی GC6","جیلی","none","none","none","none","none","none","none","ایدو","چانگان CS 35","ویانا","تیگو7","تیگو5","آریزو 6","آریزو5 TE","آریزو5","چری A15","دانگ فنگ S30","H30 کراس","دنا پلاس","دنا معمولی","نوبیرا","ماتیز","سیلو","ریسر","اسپرو","دی اس 7 کراس بک","دی اس 6","دی اس 5LS","دی اس 5","دی اس 4 کراس بک","دی اس 3","رانا LX","رانا EL","مگان","کولئوس","کپچر","فلوئنس","ساندرو استپ وی","ساندرو","داستر","تندر90","تندر وانت","تندر 90 استیشن","تالیسمان","پی کی","پارس تندر","اسکالا کروک","اسکالا","رنو GTL9","رنو5","رنو3","رنو21","نیو کوراندو","نیو اکتیون","موسو","کوراندو","رکستون","چیرمن","تیوولی","اکتیون","ساینا اتوماتیک","ساینا دنده ای","سمند سریر","سورن","سمند (LX و EL و...)","گرند ویتارا","کیزاشی","سوییفت","سامورایی","ژیان","زانتیا","سیتروئن DS21","سیتروئن C5","سیتروئن C3","گلف","گل","کمپر","کدی","کارمن","شیراکو","سانتانا","جتا","تیگوان","ترنسفورمر","ترنسپورتر","پولو","پاسات","بیتل کروک","بیتل","باگی","فولکس T3-VANAGON","فولکس T2-COMBI","فولکس LT","کاپرا دو کابین","کاپرا تک کابین","کوئیک دنده ای","کوئیک اتوماتیک","موهاوی","کارنز","کادنزا","سورنتو","سراتو کوپ","سراتو","ریو وارداتی","ریو","رتونا","پیکانتو","اینترپرایز","اسپورتیج","اپیروس","اپتیما هیبرید","اپتیما","هاوال M4","هاوال H6","وینگل 6","وینگل 5","وینگل 3","گریت وال C30","لکسوس SC","لکسوس RX هیبرید","لکسوس RX","لکسوس RC","لکسوس NX هیبرید","لکسوس NX","لکسوس LX","لکسوس LS","لکسوس IS کروک","لکسوس IS","لکسوس GX","لکسوس GS","لکسوسES","لکسوس CT","لندمارک V7","لیفان X60","لیفان X50","لیفان 820","لیفان 620","لیفان 520i","مزدا CX9","مزدا CX5","مزدا 929","مزدا 808","مزدا 626","مزدا 6","مزدا 323","مزدا 3 جدید هاچ بک","مزدا 3 جدید صندوق دار","مزدا 3","مزدا 2","ون میتسوبیشی","میراژ","مگنا","لنسر","گالانت","پاجرو","اوتلندر","اکلیپس","میتسوبیشی GT3000","میتسوبیشی ASX","وانت زامیاد","مورانو","ماکسیما","قشقایی","سرانزا","رونیز","جوک","تی ینا","پیکاپ","پاترول","ایکس تریل","آلتیما","آروان","نیسان GT-R","آمازون","ولوو XC90","ولوو XC60","ولوو XC40","ولوو V40","ولوو S90","ولوو S60","ولوو C70 کروک","ولوو C30","ولوو244","لجند","سیویک","پریلود","اینتگرا","آکورد","HONDA S800","HONDA HR-V","HONDA CR-X","HONDA CR-V","ولستر","ورنا","وراکروز (ix55)","کوپه","سوناتا هیبرید","سوناتا","سنتینیال","سانتافه ( ix45)","جنسیس کوپه","جنسیس","توسان ( ix35)","اکسنت","استارکس","آوانته","آزرا (گرنجور)","هیوندا i40","هیوندا i30","هیوندا i20","هیوندا i 10","هیوندا H1","هیوندا H350","لتیتود","سیمبل","سفران","انواع مدل ها","انواع مدل ها","آریسان","انواع مدل ها","انواع مدل ها","انواع مدل ها","ون کاسپین","ون هایما","ون دلیکا")

        val carAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, cars)
        car.setAdapter(carAdapter)

        submit_request.setOnClickListener {
            val name = client_name.text.toString()
            val gender = gender.selectedItem.toString().trim()
            val phoneNumber = client_phone.text.toString()
            val orderId = order_id.text.toString()
            val visitorId = visitor_id.text.toString()
            val car = car.text.toString()


            val count = mAdapter.itemCount
            goods.clear()
            for (i in 0 until count) {
                val view = recyclerView.getChildAt(i)
                val phone: EditText = view.findViewById(R.id.part)
                val name = phone.text.toString()
                if (name.isNotEmpty()) {
                    goods.add(name)
                }
            }
            Request(this, name, gender.trim(), phoneNumber.trim(), orderId.trim(),visitorId.trim(),car.trim(), goods).execute()
        }
    }

    inner class Request(
        private val context: Context,
        private val name: String,
        private val gender: String,
        private val phoneNumber: String,
        private val orderId: String,
        private val visitorId: String,
        private val car: String,
        private val goods: MutableList<String>
    ) : AsyncTask<String, Void, String>() {
        val pd = ProgressDialog(context)
        override fun onPreExecute() {
            pd.setMessage("در حال ارسال درخواست ...")
            pd.show()
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            pd.dismiss()
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg params: String?): String {
            val _user = DBInfo.USER
            val _pass = DBInfo.PASS
            val _DB = DBInfo.DB
            val _server = DBInfo.SERVER
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            var conn: Connection? = null
            var ConnURL: String? = null
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver")
                ConnURL = ("jdbc:jtds:sqlserver://" + _server + ";"
                        + "databaseName=" + _DB + ";user=" + _user + ";password="
                        + _pass + ";")
                conn = DriverManager.getConnection(ConnURL)


                val queryStmt =
                    "INSERT INTO dbo.Sell VALUES (${orderId},N'$name',N'$phoneNumber',N'$gender',N'${
                        format(
                            goods
                        )
                    }',N'${visitorId}',N'${car}')"
                var requests = mutableListOf<RequestInfo>()
                for (i in goods){
                    requests.add(RequestInfo(i,""))
                }
                StartActivity.orders.add(OrdersInfo(orderId,name,phoneNumber,gender,requests))
                StartActivity.ids.add(orderId)


                val stmt = conn.createStatement()
                val rslt = stmt.executeUpdate(queryStmt)

                //send sms
                sendSMS()


                stmt.close()
                conn.close()


            } catch (se: SQLException) {
                runOnUiThread {
                    // some code #3 (Write your code here to run in UI thread)
                    Toast.makeText(context, "خطا در ایجاد اتصال", Toast.LENGTH_LONG).show()
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("خطا در ایجاد اتصال (ممکن است نیاز باشد وضعیت فیلترشکن خود را تغییر دهید)")
                    builder.apply {
                        setPositiveButton("تلاش مجدد",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User clicked OK button
                                doInBackground()
                            })
                        setNegativeButton("خروج از برنامه",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                                exitProcess(0);
                            })
                    }
                    // Set other dialog properties

                    // Create the AlertDialog
                    builder.create()
                    builder.show()
                }
                se.printStackTrace()
            } catch (e: Exception) {
                runOnUiThread {
                    // some code #3 (Write your code here to run in UI thread)
                    Toast.makeText(context, "PLEASE CHECK YOUR CONNECTION!", Toast.LENGTH_LONG)
                        .show()
                }
                e.printStackTrace()
            }
            pd.dismiss()
            runOnUiThread {
                var toast = Toast.makeText(
                    context,
                    "درخواست شما با موفقیت ثبت شد",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
            context.startActivity(Intent(context, MainActivity::class.java))
            finish()
//            sendSMS()
            return ""
        }


        private fun format(goods: MutableList<String>): String {
            var parts = ""
            for (item in goods) {
                parts += ("$item;")
            }
            return parts.dropLast(1)
        }


        private fun sendSMS() {
            val from = "50004000393533"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            try {
                val soapClient = SoapClient("mahdi.jafari98", "@25506339Aa")

                soapClient.SendSimpleSMS2(
                    phoneNumber,
                    from,
                    TextsHandler.submitOrder(gender, name,TextsHandler.submitOrder),
                    false
                )

            } catch (e: java.lang.Exception) {
                Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }
}