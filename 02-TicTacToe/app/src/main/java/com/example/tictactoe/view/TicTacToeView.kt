package com.example.tictactoe.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.tictactoe.MainActivity
import com.example.tictactoe.R
import com.example.tictactoe.model.TicTacToeModel

class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground: Paint = Paint()
    private var paintLine: Paint = Paint()
    private var paintLineCircle: Paint = Paint()
    private var paintLineCross: Paint = Paint()

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 25f

        paintLineCircle.color = Color.GREEN
        paintLineCircle.style = Paint.Style.STROKE
        paintLineCircle.strokeWidth = 15f

        paintLineCross.color = Color.RED
        paintLineCross.style = Paint.Style.STROKE
        paintLineCross.strokeWidth = 15f


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        canvas?.let { drawGameArea(it) }
        canvas?.let { drawPlayers(it) }

    }

    private fun drawGameArea(canvas: Canvas) {
        // border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas.drawLine(
            0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(),
            paintLine
        )
        canvas.drawLine(
            0f, (2 * height / 3).toFloat(), width.toFloat(),
            (2 * height / 3).toFloat(), paintLine
        )

        // two vertical lines
        canvas.drawLine(
            (width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(),
            paintLine
        )
        canvas.drawLine(
            (2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(),
            paintLine
        )
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..2) {
            for (j in 0..2) {

                // draw circle
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + height / 6).toFloat()
                    val radius = height / 6 - height / 20
                    canvas.drawCircle(centerX, centerY, radius.toFloat(), paintLineCircle)

                // draw cross
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(
                        (i * width / 3 + width / 20).toFloat(),
                        (j * height / 3 + height / 20).toFloat(),
                        ((i + 1) * width / 3 - width / 20).toFloat(),
                        ((j + 1) * height / 3 - height / 20).toFloat(), paintLineCross
                    )
                    canvas.drawLine(
                        ((i + 1) * width / 3 - width / 20).toFloat(),
                        (j * height / 3 + height / 20).toFloat(),
                        (i * width / 3 + width / 20).toFloat(),
                        ((j + 1) * height / 3 - height / 20).toFloat(), paintLineCross
                    )
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 3)
            val tY = event.y.toInt() / (height / 3)

            if (tX < 3 && tY < 3 && TicTacToeModel.getFieldContent(tX, tY) == TicTacToeModel.EMPTY) {
                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())

                // érdemes itt vizsgálni hogy van-e győztes...
                // Házi feladat
                val winnerStatus = TicTacToeModel.getWinner(TicTacToeModel.getNextPlayer())

                if (winnerStatus != TicTacToeModel.CONTINUE) {
                    var winer = "O"
                    if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CROSS) winer = "X"

                    fun basicAlert(){
                        val positiveButtonClick = { _: DialogInterface, _: Int ->
                            // New game
                            resetGame()
                        }
                        val negativeButtonClick = { _: DialogInterface, _: Int ->
                            //END
                            endGame()
                        }

                        val builder = AlertDialog.Builder((context as MainActivity))
                        with(builder)
                        {
                            setTitle("The end")
                            if (winnerStatus == TicTacToeModel.DRAW)
                                setMessage("The game is draw\nNew game?")
                            else
                                setMessage("Winer is $winer\nNew game?")

                            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
                            setNegativeButton("No", negativeButtonClick)
                            //setNeutralButton("Maybe", neutralButtonClick)
                            show()
                        }
                    }

                    basicAlert()
                    invalidate()
                } else {
                    TicTacToeModel.changeNextPlayer()
                }

                showNextPlayer()
            }
        }

        return super.onTouchEvent(event)
    }

    private fun showNextPlayer() {
        var next = "O"
        if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CROSS) {
            next = "X"
        }
        (context as MainActivity).showText(
            //"Next player is: $next")
            context.resources.getString(R.string.text_next_player, next)
        )

        invalidate()
    }

    fun resetGame() {
        TicTacToeModel.resetModel()
        showNextPlayer()
        invalidate()
    }

    private fun endGame() {
        Toast.makeText((context as MainActivity), "Good bye!", Toast.LENGTH_SHORT).show()
        (context as MainActivity).finish()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }
}