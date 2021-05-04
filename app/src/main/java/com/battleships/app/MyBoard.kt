package com.battleships.app

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.ColorInt
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import java.lang.IndexOutOfBoundsException
import java.lang.Math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class MyBoard(screenWidth : Int, screenHeight : Int, context: Context) : IGameController
{

    var cell = Cell(Point(0,0), CellType.CleanWater)
    var playerBoard = arrayOf(
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
    )

    var IABoard = arrayOf(
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
            arrayOf(cell,cell,cell,cell,cell,cell,cell,cell,cell,cell),
    )

    private val graphics : Graphics
    private val TOTAL_CELLS_WIDTH = 24
    private val TOTAL_CELLS_HEIGHT = 12
    private val BOARD_SIZE = 10
    private val SCALE_FACTOR = 0.9
    private val cell_form : Float
    private var cell_size : Float
    private var screenSize : Point
    private var yOffset : Float
    private var xOffset : Float
    var showPlayerplayerBoard  = true
    var showIAplayerBoard  = true

    init {
        graphics = Graphics(screenWidth, screenHeight)
        cell_size = min(((screenWidth) / TOTAL_CELLS_WIDTH).toFloat(), ((screenHeight) / TOTAL_CELLS_HEIGHT).toFloat())
        screenSize = Point(screenWidth,screenHeight)
        cell_size += (cell_size * 0.4f)
        cell_form = (cell_size * SCALE_FACTOR).toFloat()
        yOffset  = ((screenSize.x - (BOARD_SIZE * cell_size)) / 2) + 1
        xOffset  = ((screenSize.x - (BOARD_SIZE * cell_size)) / 2) + 1
        InitPlayerBoard()
        InitIABoard()
    }

    val displayMetrics = DisplayMetrics()
    var paint = Paint()
    var rect = Rect(0, 0, 100, 100)
    var i = 0
    private fun min(a: Float, b: Float) : Float
    {
        return if(a < b) a
        else b
    }

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>) {
        for (event in touchEvents)
            if (event.type == TouchHandler.TouchType.TOUCH_UP)
            {
                Log.d("Battleships", "TAPPPPPPPPPPPPPPPPPPPP")
                IABoard[i][0].cellType = CellType.TouchedWater
                i++
            }
    }

    fun findNearestCell(x: Int, y : Int) : Cell
    {
        var nearest = Cell(Point(0,0),CellType.TouchedWater)
        var distx = -1
        var disty = -1
        var finaldist = 999999f
        playerBoard.forEach {
            for (cell in it) {
                distx = x - cell.pos.x
                disty = y - cell.pos.y

                val newDist = sqrt(pow(distx.toFloat())+ pow(disty.toFloat()))

                if(newDist < finaldist)
                {
                    finaldist = newDist
                    nearest = cell
                }
            }
        }
        return  nearest
    }

    fun pow(value : Float) : Float{ return (value * value)   }
    override fun onDrawingRequested(): Bitmap? {
        graphics.clear(Color.LTGRAY)

        if(showPlayerplayerBoard) DrawPlayerBoard()
        if(showIAplayerBoard) DrawIABoard()
        return graphics.frameBuffer
    }

    fun InitPlayerBoard()
    {
        val x = (yOffset).toInt()
        val y = cell_size.toInt()

        for (i in 0 until  BOARD_SIZE)
            for (j in 0 until  BOARD_SIZE)
            {
                val _x = x + (j * cell_size)
                val _y = y + (i * cell_size )
                playerBoard[i][j] = Cell(Point(_x.toInt(), _y.toInt()), CellType.CleanWater)
                graphics.drawRect(_x, _y, cell_form,cell_form, playerBoard[i][j].color)
            }
    }

    fun DrawPlayerBoard()
    {
        for (i in 0 until  BOARD_SIZE)
            for (j in 0 until  BOARD_SIZE)
        graphics.drawRect(  playerBoard[i][j].pos.x.toFloat(),
                            playerBoard[i][j].pos.y.toFloat(),
                            (cell_size * SCALE_FACTOR).toFloat(),
                            (cell_size * SCALE_FACTOR).toFloat(),
                            playerBoard[i][j].color)
    }

    fun InitIABoard()
    {
        val x = (yOffset)
        val y = (screenSize.y - cell_size - (BOARD_SIZE * cell_size))

        for (i in 0 until  BOARD_SIZE)
            for (j in 0 until  BOARD_SIZE)
            {
                val _x = x + (j * cell_size)
                val _y = y + (i * cell_size )
                IABoard[i][j] = Cell(Point(_x.toInt(), _y.toInt()), CellType.CleanWater)
                graphics.drawRect(_x, _y, cell_form,cell_form, IABoard[i][j].color)
            }
    }

    fun DrawIABoard()
    {
        for (i in 0 until  BOARD_SIZE)
            for (j in 0 until  BOARD_SIZE)
                graphics.drawRect(  IABoard[i][j].pos.x.toFloat(),
                        IABoard[i][j].pos.y.toFloat(),
                        (cell_size * SCALE_FACTOR).toFloat(),
                        (cell_size * SCALE_FACTOR).toFloat(),
                        IABoard[i][j].color)
    }

}