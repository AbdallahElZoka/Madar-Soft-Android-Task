package com.dev_abdallah_el_zoka.madarsoftandroidtask.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo.ApplicantModel

@Database(
    entities = [
        ApplicantModel::class
    ], version = 1
)
abstract class ApplicantsRoomDatabase : RoomDatabase() {

    companion object {
        private const val databaseName = "Applicants Database"
        fun getInstance(context: Context): ApplicantsRoomDatabase {
            return Room.databaseBuilder(
                context,
                ApplicantsRoomDatabase::class.java, databaseName
            ).build()
        }
    }

    abstract fun getApplicantsDAO(): ApplicantsDAO

}