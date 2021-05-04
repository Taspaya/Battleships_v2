package com.battleships.app

import android.util.DisplayMetrics
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.IGameController

class activity_game : GameActivity( ) {

    private  lateinit var myBoard: MyBoard

    override fun buildGameController(): IGameController {

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        myBoard = MyBoard(displayMetrics.widthPixels, displayMetrics.heightPixels, applicationContext)
        return myBoard

    }
}


