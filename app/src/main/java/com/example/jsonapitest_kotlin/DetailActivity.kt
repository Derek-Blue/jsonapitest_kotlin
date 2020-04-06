package com.example.jsonapitest_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import com.example.jsonapitest_kotlin.Tools.UrlImageView

class DetailActivity : AppCompatActivity() {

    lateinit var close : ImageView
    lateinit var imageView: UrlImageView
    lateinit var id_txt : TextView
    lateinit var title_txt : TextView

    val id by lazy { intent?.extras?.get("id")as Int }
    val title by lazy { intent?.extras?.get("title")as String }
    val url by lazy { intent?.extras?.get("url")as String }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        close = findViewById(R.id.btn_back)
        imageView = findViewById(R.id.detail_image)
        id_txt = findViewById(R.id.detail_id)
        title_txt = findViewById(R.id.detail_title)

        id_txt.text = "ID : $id"
        title_txt.text = "Title : $title"
        imageView.setImageURL(url)

        close.setOnClickListener {
            finish()
        }
    }

    //覆寫返回鍵回上一頁 不讓程式直接退出
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        finish()
        return super.onKeyDown(keyCode, event)
    }
}
