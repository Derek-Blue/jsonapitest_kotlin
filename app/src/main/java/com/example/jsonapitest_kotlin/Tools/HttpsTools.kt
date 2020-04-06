package com.example.jsonapitest_kotlin.Tools

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object HttpsTools {

    //數據輸出工具
//    companion object{
//        fun resolveInpustream(inputStream: InputStream) : String{
//
//            val result : ByteArray
//            try {
//                val outputStream = ByteArrayOutputStream()
//                val buff = ByteArray(1024)
//                var len : Int
//
//                while (((inputStream.read(buff)).also { len = it }) != -1){
//                    outputStream.write(buff, 0, len)
//                }
//
//                inputStream.close()
//                outputStream.close()
//                result = outputStream.toByteArray()
//
//            }catch (e: IOException) {
//                e.printStackTrace()
//                return "解析錯誤!"
//            }
//
//            return String(result)
//        }
//    }

    fun resolveInpustream(inputStream: InputStream) : String{

        val result : ByteArray
        try {
            val outputStream = ByteArrayOutputStream()
            val buff = ByteArray(1024)
            var len : Int

            while (((inputStream.read(buff)).also { len = it }) != -1){
                outputStream.write(buff, 0, len)
            }

            inputStream.close()
            outputStream.close()
            result = outputStream.toByteArray()

        }catch (e: IOException) {
            e.printStackTrace()
            return "解析錯誤!"
        }

        return String(result)
    }
}