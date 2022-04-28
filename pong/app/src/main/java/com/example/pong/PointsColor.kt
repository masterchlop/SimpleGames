package com.example.pong

import android.graphics.Paint

class PointsColor(s: String) : Paint() {
    var pointsCount= Paint()
    init {
        pointsCount.textSize=100f
        when (s) {
            "pink" -> pointsCount.setARGB(150, 255, 25, 151)
            "red" -> pointsCount.setARGB(150, 221, 8, 47)
            "yellow" -> pointsCount.setARGB(150, 255, 248, 58)
            "green" -> pointsCount.setARGB(150, 28, 255, 50)
            "cyan" -> pointsCount.setARGB(150, 65, 220, 244)
            "blue" -> pointsCount.setARGB(150, 55, 55, 242)
            "grey" -> pointsCount.setARGB(150, 160, 160, 160)
            "purple" -> pointsCount.setARGB(150, 60, 160, 160)
        }
    }
}
