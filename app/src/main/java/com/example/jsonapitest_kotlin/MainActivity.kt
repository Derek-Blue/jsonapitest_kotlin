package com.example.jsonapitest_kotlin

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Space
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonapitest_kotlin.Adapter.ItemAdapter
import com.example.jsonapitest_kotlin.Model.Photo
import com.example.jsonapitest_kotlin.Tools.HttpsTools
import com.example.jsonapitest_kotlin.Tools.ProgressWindow
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val path = "https://jsonplaceholder.typicode.com/photos"
    lateinit var back : ImageView
    lateinit var photoList : ArrayList<Photo>
    lateinit var recyclerView: RecyclerView
    var itemAdapter: ItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        back = findViewById(R.id.btn_back)
        back.setOnClickListener {
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(500) //暫存Item數
        val layoutManager = GridLayoutManager(this, 4)
        layoutManager.initialPrefetchItemCount = 48
        recyclerView.layoutManager = layoutManager
        itemAdapter = ItemAdapter(this.applicationContext, ArrayList())
        recyclerView.adapter = itemAdapter
        recyclerView.itemAnimator = DefaultItemAnimator()

        ConnectApi().execute(path)//onCreat 直接執行
    }

    //非同步作業
    private inner class ConnectApi : AsyncTask<String, Void, List<Photo>>() {

        internal val progress =
            ProgressWindow(this@MainActivity, R.style.PrDialog)

        override fun onPreExecute() {
            super.onPreExecute()
            progress.show()
        }

        override fun doInBackground(vararg params: String?): List<Photo>? {

            var url : URL? = null
            photoList = ArrayList()
            try {
                //網路請求
                url = URL(params[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val inputStream = connection.inputStream
                val jsonResult = HttpsTools.resolveInpustream(inputStream)//傳入輸出工具

                val jsonArray = JSONArray(jsonResult)
                for (i in 0 until jsonArray.length()){

                    val result = jsonArray.getJSONObject(i)
                    photoList.add(convertPhoto(result))

                }
                return photoList
            }catch (e : Exception){
                e.printStackTrace()
            }
            return null
        }

        //完成後執行
        override fun onPostExecute(result: List<Photo>?) {
            super.onPostExecute(result)
            //itemAdapter!!.setPhotos(photoList)
            itemAdapter!!.photos = photoList
            itemAdapter!!.notifyDataSetChanged()
            progress.cancel()
        }
    }

    @Throws(JSONException::class)
    private fun convertPhoto(jsonObject: JSONObject) : Photo {
        //資料Model
        val id = jsonObject.getInt("id")
        val title = jsonObject.getString("title")
        val thumbnailUrl = jsonObject.getString("thumbnailUrl")

        return Photo(id, title, thumbnailUrl)
    }
}
