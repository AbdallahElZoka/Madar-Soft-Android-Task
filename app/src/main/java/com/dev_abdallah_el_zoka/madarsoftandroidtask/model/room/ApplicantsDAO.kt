package com.dev_abdallah_el_zoka.madarsoftandroidtask.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo.ApplicantModel

@Dao
interface ApplicantsDAO {

    @Insert(entity = ApplicantModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplicantDataIntoRoomDatabase(applicantModel: ApplicantModel)

    @Query("SELECT * FROM `Applicants Table`")
    suspend fun getAllApplicantsFromRoomDatabase(): List<ApplicantModel>
}