package com.example.smsapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_manage_texts.*

class ManageTexts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_texts)
        getSupportActionBar()?.hide();

        first.setText(TextsHandler.submitOrder)
        second.setText(TextsHandler.sendLink)
        fifth.setText(TextsHandler.tellPrice)
        sixth.setText(TextsHandler.sendRecieveSms)
        seventh.setText(TextsHandler.finalOrder)
        eighth.setText(TextsHandler.takeToTransport)
        ninth.setText(TextsHandler.takeToFreight)

        button3.setOnClickListener {
            TextsHandler.submitOrder = first.text.toString()
            TextsHandler.sendLink = second.text.toString()
            TextsHandler.tellPrice = fifth.text.toString()
            TextsHandler.sendRecieveSms = sixth.text.toString()
            TextsHandler.finalOrder = seventh.text.toString()
            TextsHandler.takeToTransport = eighth.text.toString()
            TextsHandler.takeToFreight = ninth.text.toString()
            Toast.makeText(this,"تغییرات اعمال شد",Toast.LENGTH_SHORT).show()
        }


    }
}