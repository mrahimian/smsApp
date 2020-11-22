package com.example.smsapp

data class OrdersInfo (val order_id : String, val clientName : String, val clientPhone : String, val gender : String, val goodsInfo : MutableList<RequestInfo>)