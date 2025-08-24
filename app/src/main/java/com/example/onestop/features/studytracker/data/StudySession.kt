package com.example.onestop.features.studytracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Study_sessions")
data class StudySession(
    @PrimaryKey(autoGenerate = true)
    //val id=Int nhi likhna cause that means that the value is equal to int and val id : Int=0 means ki the id will be of type integer
    val id:Int=0,
//    val subject: String,
    val startTime: Long,
    val endTime:Long,
    val duration:Long,
    val date: String
)
