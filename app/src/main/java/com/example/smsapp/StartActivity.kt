package com.example.smsapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_start.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class StartActivity : AppCompatActivity() {

    companion object {
        val orders = mutableListOf<OrdersInfo>()
        val ids = arrayListOf<String>()
    }

    var flag1 = false
    var flag2 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        GetData(this).execute()

        logo.animate().alpha(1.0f).duration = 2000
        var ra = TranslateAnimation(
            logo.translationX,
            logo.translationX,
            logo.translationY,
            (logo.translationY - 100)
        )
        ra.duration = 2000
        ra.fillAfter = true;
        ra.isFillEnabled = true;
//        GetData(this).execute()

        ra.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                flag1 = true
                if (flag1 && flag2) {
                    startActivity(Intent(this@StartActivity, MainActivity::class.java))
                    finish()
                }

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        logo.startAnimation(ra)

    }

    inner class GetData(val context: Context) : AsyncTask<String, Void, String>() {


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun doInBackground(vararg params: String?): String {

            val _user = "sa"
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
                    "select * from dbo.Sell order by order_id"


                val stmt = conn.createStatement()
                val stmt2 = conn.createStatement()
                val rslt = stmt.executeQuery(queryStmt)
//                val rslt2 = stmt2.executeQuery(queryStmt2)

                while (rslt.next()) {
                    var id = rslt.getString(1)
                    var name = rslt.getString(2)
                    var phone = rslt.getString(3)
                    var gender = rslt.getString(4)
                    var goods = rslt.getString(5)
                    val parts = goods.split(";")
                    val requestInfo = mutableListOf<RequestInfo>()
                    for (part in parts) {
                        requestInfo.add(RequestInfo(part, ""))
                    }
                    orders.add(
                        OrdersInfo(
                            id, name, phone, gender, requestInfo
                        )
                    )
                }
                allIds()

                flag2 = true
                if (flag1 && flag2) {
                    startActivity(Intent(this@StartActivity, MainActivity::class.java))
                    finish()
                }


//                val tableInfoQuery = "select * from dbo.Stock2"
//                val result = stmt.executeQuery(tableInfoQuery)


                stmt.close()
                conn.close()
                rslt.close()
//                rslt2.close()
//                rslt3.close()

            } catch (se: SQLException) {
                runOnUiThread {
                    // some code #3 (Write your code here to run in UI thread)
                    Toast.makeText(context, "خطا در ایجاد اتصال", Toast.LENGTH_LONG).show()
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("خطا در ایجاد اتصال (ممکن است نیاز باشد فیلترشکن خود را روشن کنید)")
                    builder.apply {
                        setPositiveButton("اتصال مجدد",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User clicked OK button
                                doInBackground()
                            })
                        setNegativeButton("خروج از برنامه",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                                System.exit(0);
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
            return ""
        }

        private fun allIds() {
            for (i in orders) {
                ids.add(i.order_id)
            }
        }


    }
}