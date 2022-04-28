package com.example.pong

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import kotlin.random.Random
import android.graphics.BitmapFactory

class Ball(var image: Bitmap) {
    var x:Int =0
     var y: Int  =0
    var size: Int=0
    var speedx:Int=0
    var speedy:Int=0
    var Recordofbounces =0

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels


    init {
         size= image.height
         reset(Random.nextBoolean())

     }
    fun draw(canvas: Canvas){
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(),null)


    }
    fun reset(player1: Boolean){
        Recordofbounces=0
        x = screenWidth / 2
        y = screenHeight / 2
        if (player1){
            speedx =10
            speedy = 10
        }
        else {
            speedx =-10
            speedy= -10
        }

    }
    fun update(){
        x+=speedx
        y+=speedy
        if(x<=0||x+size>=screenWidth)
            speedx=-speedx

    }
    fun updatespeed(){
        if(speedx<0){
            speedx-=1

        }
        else speedx+=1

        if (speedy<0){
            speedy-=1
        }
        else speedy+=1


    }
}
