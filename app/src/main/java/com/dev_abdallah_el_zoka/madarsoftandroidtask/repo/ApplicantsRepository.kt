package com.dev_abdallah_el_zoka.madarsoftandroidtask.repo

import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.room.ApplicantsRoomDatabase
import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo.ApplicantModel

class ApplicantsRepository(val roomDatabase: ApplicantsRoomDatabase) {

    suspend fun saveApplicantsInDatabase(applicantModel: ApplicantModel) {
        roomDatabase.getApplicantsDAO()
            .insertApplicantDataIntoRoomDatabase(applicantModel = applicantModel)
    }

    suspend fun getApplicantsFromDatabase(): List<ApplicantModel> {
        return roomDatabase.getApplicantsDAO().getAllApplicantsFromRoomDatabase()
    }
}