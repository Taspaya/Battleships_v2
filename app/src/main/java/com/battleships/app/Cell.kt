package com.battleships.app

import android.graphics.Color
import android.graphics.Point

public  enum class CellType{
    TouchedWater,
    CleanWater,
    TouchedShip,
    CleanShip
}

class Cell (val pos : Point, _cellType: CellType )
{
    val lightBlueWater = Color.rgb(7, 140,178)
    val darkBlueWater = Color.rgb(4, 108,138)
    var color : Int
    var cellType : CellType = _cellType
        set(value) {
            field = value
            if(cellType == CellType.CleanWater)
                color = lightBlueWater
            else if (cellType == CellType.TouchedWater)
                color = darkBlueWater
            else if(cellType == CellType.TouchedShip)
                color = Color.RED
            else
                color = Color.YELLOW
        }

    init {
        if(cellType == CellType.CleanWater)
            color = lightBlueWater
        else if (cellType == CellType.TouchedWater)
            color = darkBlueWater
        else if(cellType == CellType.TouchedShip)
            color = Color.RED
        else
            color = Color.YELLOW

    }

}