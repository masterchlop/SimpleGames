package com.example.pong.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "score")
@Parcelize
data class Score(
        var RecordOfBounces:Int = 0,

        var Paddle1:Int =0,
        var Paddle2: Int =0,
        var Ballx:Int =0,
        var Bally:Int = 0,
        var Score1:Int =0,
        var Score2:Int = 0,
        @PrimaryKey(autoGenerate = true) val id: Int=0
      ): Parcelable