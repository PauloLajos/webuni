package com.example.tictactoe.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground: Paint = Paint()
    private var paintLine: Paint = Paint()

    private var x: Float = -1f
    private var y: Float = -1f

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        canvas?.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        canvas?.drawCircle(x, y, 50f, paintLine)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        x = event.x
        y = event.y

        invalidate()

        //return super.onTouchEvent(event)
        return true
    }
}