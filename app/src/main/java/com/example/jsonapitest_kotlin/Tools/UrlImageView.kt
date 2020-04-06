package com.example.jsonapitest_kotlin.Tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import android.util.AttributeSet

import android.widget.ImageView
import android.widget.Toast
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class UrlImageView : ImageView {

    //自定義加入 解析Url方法 & 設定View比例

    companion object { //靜態變數
        val GET_SUCCESS = 1
        val NETWORK_ERROR = 2
        val SERVER_ERROR = 3
    }

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message){
            when (msg.what){
                GET_SUCCESS -> {
                    val bitmap = msg.obj as Bitmap
                    setImageBitmap(bitmap)
                }
                SERVER_ERROR -> Toast.makeText(context, "網址服務錯誤!", Toast.LENGTH_SHORT).show()
                NETWORK_ERROR -> Toast.makeText(context, "連線失敗!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setImageURL(path : String) {
        object : Thread(){
            override fun run() {
                var bitmap : Bitmap?
                try {
                    val url = URL(path)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    val code = connection.responseCode
                    if (code == 200){
                        val inputStream = connection.inputStream
                        bitmap = BitmapFactory.decodeStream(inputStream)
                        val msg = Message.obtain()
                        msg.obj = bitmap
                        msg.what =
                            GET_SUCCESS
                        handler.sendMessage(msg)
                        inputStream.close()
                    }else{
                        handler.sendEmptyMessage(SERVER_ERROR)
                    }
                }catch (e : IOException){
                    e.printStackTrace()
                    handler.sendEmptyMessage(NETWORK_ERROR)
                }
            }
        }.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //固定比例 1:1
        var setHeight = heightMeasureSpec
        var width = MeasureSpec.getSize(widthMeasureSpec)
        if (width != 0){
            setHeight = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, setHeight)
    }
}