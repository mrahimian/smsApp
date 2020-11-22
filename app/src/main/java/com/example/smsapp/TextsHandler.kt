package com.example.smsapp

class TextsHandler {
    companion object{
        var submitOrder = "مشتری گرامی ، " + "(جنسیت)" + " " + "(نام)" + " سفارش شما در سامانه سفارشات یدک پرو ثبت گردید. کارشناسان ما به زودی با شما تماس خواهند گرفت."
        var tellPrice = "مشتری گرامی ، " + "(جنسیت)" + " " + "(نام)" + " جزئیات سفارش شما به شرح زیر می باشد :" + "\n" + "(سفارشات)" + "\n" + " لطفا کد کالاهای درخواستی خود را پیامک کنید"
        var sendRecieveSms = "مشتری گرامی ، " + "(جنسیت)" + " " + "(نام)" + " سفارش شما در محل مدنظر به " + "(جنسیت تحویل گیرنده)" + " " + "(نام تحویل گیرنده)" + " تحویل گردید. از اعتماد شما به مجموعه یدک پرو سپاسگزاریم."
        var finalOrder = "مشتری گرامی ، " + "(جنسیت)" + " " + "(نام)" + " " +"سفارش شما تکمیل شده و در حال پردازش می باشد"
        var takeToTransport =  "مشتری گرامی ، " + "(جنسیت)" + " " + "(نام)" +" سفارش شما پردازش شده و جهت ارسال به واحد حمل و نقل یدک پرو تحویل گردید"
        var takeToFreight = "مشتری گرامی ، " + "(جنسیت)" + " " + "(نام)" + " " + "سفارش شما جهت حمل ، به بابری " + "(نام باربری)" + " تحویل گردید" + "\n" + "شماره قبض : " + "(قبض)" + "\n" + "شماره باربری : " + "(شماره باربری)" + "\n" + "از اعتماد شما به یدک پرو سپاسگزاریم"
        var sendLink = "مشتری گرامی ، " + "(جنسیت)" + " " + "(نام)" + " " +" سفارش شما آماده ارسال است. لطفا از طریق لینک زیر نسبت به تکمیل سفارش و پرداخت صورتحساب خود اقدام فرمایید. " + "\n" + "(لینک پرداخت)"

        fun submitOrder(gender : String , name : String , text : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var clone = text.replace("(نام)",name)
            clone = clone.replace("(جنسیت)",gender2)
            return clone
        }

        fun tellPrice(gender : String , name : String , orders : ArrayList<RequestInfo> , text : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var clone = text.replace("(نام)",name)
            clone = clone.replace("(جنسیت)",gender2)
            var goods = ""
            for (i in 0 until orders.size){
                goods += if (i == orders.size - 1){
                    ("" + (i+1) + "_ " + orders[i].part + " : " + orders[i].price + " تومان" )
                }else {
                    ("" + (i + 1) + "_ " + orders[i].part + " : " + orders[i].price + " تومان" + "\n")
                }
            }
            clone = clone.replace("(سفارشات)",goods)
            return clone
        }

        fun sendReceiveSms(gender1 : String, name1 : String, gender2 : String, name2 : String , text : String):String{
            val genderNew : String = if (gender1.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            val genderNew2 : String = if (gender2.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var clone = text.replace("(نام)",name1)
            clone = clone.replace("(جنسیت)",genderNew)
            clone = clone.replace("(جنسیت تحویل گیرنده)",genderNew2)
            clone = clone.replace("(نام تحویل گیرنده)",name2)
            return clone
        }

        fun finalOrder(gender : String , name : String , text : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var clone = text.replace("(نام)",name)
            clone = clone.replace("(جنسیت)",gender2)
            return clone
        }

        fun takeToTransport(gender : String , name : String , text : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var clone = text.replace("(نام)",name)
            clone = clone.replace("(جنسیت)",gender2)
            return clone
        }

        fun takeToFreight(gender : String , name : String , name2 : String  , id : String , freightNum : String, text : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var clone = text.replace("(نام)",name)
            clone = clone.replace("(جنسیت)",gender2)
            clone = clone.replace("(نام باربری)",name2)
            clone = clone.replace("(قبض)",id)
            clone = clone.replace("(شماره باربری)",freightNum)
            return clone
        }


        fun submitOrder(gender : String , name : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            return "مشتری گرامی ، " + gender2 + " " + name + " سفارش شما در سامانه سفارشات یدک پرو ثبت گردید. کارشناسان ما به زودی با شما تماس خواهند گرفت."
        }
        fun tellPrice(gender : String , name : String , orders : ArrayList<RequestInfo>):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var final = "مشتری گرامی ، " + gender2 + " " + name + " جزئیات سفارش شما به شرح زیر می باشد :" + "\n"
            for (i in 0 until orders.size){
                final += ("" + (i+1) + "_ " + orders[i].part + " : " + orders[i].price + " تومان" + "\n")
            }
            final += " لطفا کد کالاهای درخواستی خود را پیامک کنید"
            return final
        }

        fun sendRecieveSms(gender1 : String, name1 : String, gender2 : String, name2 : String):String{
            val genderNew : String = if (gender1.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            val genderNew2 : String = if (gender2.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            return "مشتری گرامی ، " + genderNew + " " + name1 + " سفارش شما در محل مدنظر به " + genderNew2 + " " + name2 + " تحویل گردید. از اعتماد شما به مجموعه یدک پرو سپاسگزاریم."
        }

        fun finalOrder(gender : String , name : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            return "مشتری گرامی ، " + gender2 + " " + name + " " +"سفارش شما تکمیل شده و در حال پردازش می باشد"
        }

        fun takeToTransport(gender : String , name : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            return "مشتری گرامی ، " + gender2 + " " + name +"سفارش شما پردازش شده و جهت ارسال به واحد حمل و نقل یدک پرو تحویل گردید"
        }

        fun takeToFreight(gender : String , name : String , name2 : String  , id : String , freightNum : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            return "مشتری گرامی ، " + gender2 + " " + name + " " + "سفارش شما جهت حمل ، به بابری " + name2 + " تحویل گردید" + "\n" + "شماره قبض : " + id + "\n" + "شماره باربری : " + freightNum + "\n" + "از اعتماد شما به یدک پرو سپاسگزاریم"
        }

        fun tellPriceToCompleteOrder(gender : String , name : String ):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            return "مشتری گرامی ، " + gender2 + " " + name + " سفارش شما آماده ارسال است. لطفا از طریق لینک زیر نسبت به تکمیل سفارش خود اقدام فرمایید. "
        }

        fun sendPaymentLink(gender : String , name : String , link : String , text : String ):String {
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var clone = text.replace("(نام)",name)
            clone = clone.replace("(جنسیت)",gender2)
            clone = clone.replace("(لینک پرداخت)",link)
            return clone
        }

        fun sendPaymentLink(gender : String , name : String , text : String):String{
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            var clone = text.replace("(نام)",name)
            clone = clone.replace("(جنسیت)",gender2)
            return clone
        }

        fun sendPoll(gender : String , name : String ):String {
            val gender2 = if (gender.trim() == "آقا"){
                "جناب آقای"
            }else{
                "سرکار خانم"
            }
            return "مشتری گرامی ، " + gender2 + " " + name + " از اینکه یدک پرو را انتخاب کرده اید سپاسگزاریم. لطفا با پر کردن فرم نظر سنجی از طریق لینک زیر ما را در ارائه هر چه بهتر خدمات یاری فرمایید "
        }

    }
}