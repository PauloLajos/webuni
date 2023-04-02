package com.example.tictactoe.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.tictactoe.MainActivity
import com.example.tictactoe.model.TicTacToeModel

class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground: Paint = Paint()
    private var paintLine: Paint = Paint()

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
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + height / 6).toFloat()
                    val radius = height / 6 - height / 20

                    canvas.drawCircle(centerX, centerY, radius.toFloat(), paintLine)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(
                        (i * width / 3 + width / 20).toFloat(),
                        (j * height / 3 + height / 20).toFloat(),
                        ((i + 1) * width / 3 - width / 20).toFloat(),
                        ((j + 1) * height / 3 - height / 20).toFloat(), paintLine
                    )

                    canvas.drawLine(
                        ((i + 1) * width / 3 - width / 20).toFloat(),
                        (j * height / 3 + height / 20).toFloat(),
                        (i * width / 3 + width / 20).toFloat(),
                        ((j + 1) * height / 3 - height / 20).toFloat(), paintLine
                    )
                }
            }
        }
    }

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

                    fun basicAlert(view: View){
                        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                            resetGame()
                        }
                        val negativeButtonClick = {dialog: DialogInterface, which: Int ->
                            //END
                        }

                        val builder = AlertDialog.Builder((context as MainActivity))
                        with(builder)
                        {
                            setTitle("The end")
                            setMessage("Winer is $winer\nNew game?")
                            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
                            setNegativeButton("No", negativeButtonClick)
                            //setNeutralButton("Maybe", neutralButtonClick)
                            show()
                        }
                    }

                    basicAlert(this)
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
        (context as MainActivity).showText("Next player is: $next")

        invalidate()
    }

    public fun resetGame() {
        TicTacToeModel.resetModel()
        showNextPlayer()
        invalidate()
    }
}