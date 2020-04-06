package com.example.jsonapitest_kotlin.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonapitest_kotlin.DetailActivity
import com.example.jsonapitest_kotlin.Model.Photo
import com.example.jsonapitest_kotlin.R
import com.example.jsonapitest_kotlin.Tools.UrlImageView
import java.lang.reflect.Array

class ItemAdapter( var context: Context, var photos: ArrayList<Photo>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

//    fun setPhotos(photos: ArrayList<Photo>){
//        this.photos = photos
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemview, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        var height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        holder.itemView.measure(width, height)
        height = holder.itemView.measuredHeight//取view高度
        val fontsize = fontSize(height)
        Log.d("fontsize=",""+fontsize)
        holder.post_title.textSize = fontsize.toFloat()

        val photo = photos[position]
        holder.post_image.setImageResource(R.mipmap.ic_progress)//佔位符
        holder.post_id.text = photo.id.toString()
        holder.post_title.text = photo.title

        if (photo.imageUrl != null){
            //擷取原圖片網址色碼部分
            var color = photo.imageUrl!!.substring(32, photo.imageUrl!!.length)
            //因　ipsumimage　提供色碼方式與　placeholder　不同　４碼或５碼會有無法服務的狀況
            //色碼缺失部分用暫用０補上
            if (color.length == 4){
                color += "00"
            }else if (color.length == 5){
                color += "0"
            }
            Log.v("V200", "" + color)
            //移到ipsumimage網站取圖
            val urlResult = "http://ipsumimage.appspot.com/150,$color"
            holder.post_image.tag = urlResult

            if (urlResult == holder.post_image.tag){    //再判斷一次 避免圖片亂跑
                holder.post_image.setImageURL(urlResult)
            }
        }

        holder.itemView.setOnClickListener{
            //傳值&進入到第三頁
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", photo.id)
            intent.putExtra("title", photo.title)
            intent.putExtra("url", holder.post_image.tag.toString())
            context.startActivity(intent)
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var post_image = view.findViewById<UrlImageView>(R.id.post_image)
        var post_id = view.findViewById<TextView>(R.id.post_id)
        var post_title = view.findViewById<TextView>(R.id.post_title)

//        constructor(itemView: View) : super(itemView){
//            post_id = itemView.findViewById(R.id.post_id)
//            post_title = itemView.findViewById(R.id.post_title)
//            post_image = itemView.findViewById(R.id.post_image)
//        }
    }

    private fun fontSize(height : Int) : Int {
        //依高度返回要設定的字型大小
        return if (height < 55){
            9
        } else if (height >= 55 && height < 70) {
            10
        } else {
            11
        }
    }
}