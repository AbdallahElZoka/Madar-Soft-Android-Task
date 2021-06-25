package com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Applicants Table")
data class ApplicantModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val applicantName: String? = null,
    val jobTitle: String? = null,
    val age: Int? = null,
    val gender: String? = null
)
