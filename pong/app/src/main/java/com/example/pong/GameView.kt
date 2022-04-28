package com.example.pong

import android.content.Context

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.room.Room
import com.example.pong.data.AppDatabase

import com.example.pong.data.Score


class GameView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet),
        SurfaceHolder.Callback {
    private  var database: AppDatabase


    private var color1 = PointsColor( "green")
    private var color2 = PointsColor("red")
    private var thread: GameThread

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    private var touchedX:Int =0
    private var touchedY: Int =0

    private var pointsColor1: Paint
    private var pointsColor2: Paint
    init{
        holder.addCallback(this)
        thread = GameThread(holder,this)
        pointsColor1 =color1.pointsCount
        pointsColor2=color2.pointsCount
        database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "score2.db"
        ).allowMainThreadQueries().build()


    }
    
    private var s=database.ScoreDAO().getAllbounces()

    private var ball: Ball? = null



    private var Recordofbounces = s


    private var paddle1: Padle? =null
    private var paddle2: Padle? =null


    override fun surfaceCreated(holder: SurfaceHolder) {




        ball = Ball(BitmapFactory.decodeResource(resources, R.drawable.ball))

            paddle1 = Padle(BitmapFactory.decodeResource(resources, R.drawable.paddle1), 1)
            paddle2 = Padle(BitmapFactory.decodeResource(resources, R.drawable.paddle2), 2)


        thread.setRunning(true)
        thread.start()
    }







    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {


        Log.i("TAG", "MyClass.getView() — get item number $Recordofbounces")
        var b=Score(Recordofbounces, paddle1!!.x, paddle2!!.x,ball!!.x,ball!!.y,paddle1!!.points,paddle2!!.points)
        database.ScoreDAO().insercik(b)

        thread.setRunning(false)
        thread.join()
    }


    override fun draw(canvas: Canvas){
        super.draw(canvas)
        ball!!.draw(canvas)
        paddle1!!.draw(canvas)
        paddle2!!.draw(canvas)

        drawGameInfo(canvas)
        holder.unlockCanvasAndPost(canvas)

    }

    private fun drawGameInfo(canvas: Canvas) {
        canvas.save()
        canvas.rotate(180f, screenWidth / 2f, screenHeight / 2f)
        pointsColor1.textSize = 100f
        canvas.drawText(
                "${paddle1!!.points}",
                screenWidth - 0.15f * screenWidth,
                screenHeight / 2f + 0.1f * screenHeight,
                pointsColor1
        )
        pointsColor1.textSize = 50f
        canvas.drawText(
                "Recordofbounces: $Recordofbounces",
                0.05f * screenWidth,
                screenHeight / 2f + 0.08f * screenHeight,
                pointsColor1
        )
        canvas.restore()
        canvas.drawText(
                "${paddle2!!.points}",
                screenWidth - 0.15f * screenWidth,
                screenHeight / 2f + 0.1f * screenHeight,
                pointsColor2
        )
        pointsColor2.textSize = 50f
        canvas.drawText(
                "Recordofbounces: $Recordofbounces",
                0.05f * screenWidth,
                screenHeight / 2f + 0.08f * screenHeight,
                pointsColor2
        )
    }

    fun update(){
        val paddle1Rect = Rect(paddle1!!.x, paddle1!!.y, paddle1!!.x + paddle1!!.width, paddle1!!.y + paddle1!!.height)
        val paddle2Rect = Rect(paddle2!!.x, paddle2!!.y, paddle2!!.x + paddle2!!.width, paddle2!!.y + paddle2!!.height)
        val ballRect = Rect(ball!!.x, ball!!.y, ball!!.x + ball!!.size, ball!!.y + ball!!.size)

        if (paddle1Rect.intersect(ballRect)) {
            ball!!.updatespeed()
            ball!!.speedy = -ball!!.speedy
            ball!!.Recordofbounces++
        } else if (paddle2Rect.intersect(ballRect)) {
            ball!!.updatespeed()
            ball!!.speedy = -ball!!.speedy
            ball!!.Recordofbounces++
        } else if (ball!!.y < 0) {
            paddle2!!.points++
            ball!!.reset(false)
        } else if (ball!!.y > screenHeight - ball!!.size) {
            paddle1!!.points++
            ball!!.reset(true)
        }

        if (ball!!.Recordofbounces> Recordofbounces) {
            Recordofbounces = ball!!.Recordofbounces

            database.ScoreDAO().Updatescore(Recordofbounces)
        }

        ball!!.update()
        paddle1!!.update()
        paddle2!!.update()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        val pointerCount = event.pointerCount

        for (i in 0 until pointerCount) {

            touchedX = event.getX(i).toInt()
            touchedY = event.getY(i).toInt()

            if (touchedY < height / 2) {
                paddle1!!.x = touchedX - (paddle2!!.image.width / 2)
                paddle1!!.update()
            } else {
                paddle2!!.x = touchedX - (paddle1!!.image.width / 2)
                paddle2!!.update()
            }
        }
        return true
    }



}