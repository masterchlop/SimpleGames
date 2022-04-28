package com.example.pong.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDAO {

    @Query("SELECT RecordOfBounces FROM score WHERE id=1")
    fun getAllbounces() : Int

    @Insert
    fun insercik(vararg score: Score)

    @Query("UPDATE score SET RecordOfBounces =  :pkt WHERE id = 1  ")
     fun Updatescore(pkt:Int)


    @Query("INSERT INTO score (RecordOfBounces,Paddle1,Paddle2,Ballx,Bally,Score1,Score2) VALUES (1,0,0,0,0,0,0)")
    fun insert2()

    @Query("SELECT RecordOfBounces from score Order by RecordOfBounces Desc Limit 1")
    fun naj():Int


}