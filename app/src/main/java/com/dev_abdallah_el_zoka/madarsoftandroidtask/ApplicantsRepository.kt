package com.dev_abdallah_el_zoka.madarsoftandroidtask

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicantsRepository(val roomDatabase: ApplicantsRoomDatabase) {

    suspend fun saveApplicantsInDatabase(applicantModel: ApplicantModel) {
        roomDatabase.getApplicantsDAO()
            .insertApplicantDataIntoRoomDatabase(applicantModel = applicantModel)
    }

    suspend fun getApplicantsFromDatabase() {
        roomDatabase.getApplicantsDAO().getAllApplicantsFromRoomDatabase()
    }
}