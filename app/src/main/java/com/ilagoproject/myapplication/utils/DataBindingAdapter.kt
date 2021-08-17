package com.ilagoproject.myapplication.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

object DataBindingAdapter {
        @JvmStatic
        @BindingConversion
        fun longToString(long: Long) : String{
            return long.toString()
        }

        @JvmStatic
        @BindingConversion
        fun doubleToString(double: Double) : String{
            return double.toString()
        }

        @JvmStatic
        @BindingAdapter("app:dateUnix")
        fun dateFromUnix(view: TextView, date: Long){
            view.text = SimpleDateFormat("dd/MM/yy HH:mm:ss").format(Date(date * 1000))
        }

        @JvmStatic
        @BindingAdapter("app:srcUrl", "app:errorImg", "app:processImg")
        fun imageFromUrl(view: ImageView, url:String, error: Drawable, process:Drawable){
            Picasso.get().load(url).placeholder(process).error(error).into(view)
        }
}