package com.example.jsonapitest_kotlin.Tools

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.jsonapitest_kotlin.R

class ProgressWindow : Dialog {

    //自定義ProgressDiolog

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId){
        setWindow()
    }
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    fun setWindow(){
        val window = getWindow() as Window
        window.setBackgroundDrawableResource(android.R.color.transparent)//window透明
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val view = LayoutInflater.from(context).inflate(R.layout.progress_item, null) as View
        setContentView(view)
        super.onCreate(savedInstanceState)
    }
}