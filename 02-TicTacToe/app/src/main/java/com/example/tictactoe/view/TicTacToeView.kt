package com.example.tictactoe.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    lateinit var paintBackground: Paint

    init {
        paintBackground = Paint()
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
    }
}