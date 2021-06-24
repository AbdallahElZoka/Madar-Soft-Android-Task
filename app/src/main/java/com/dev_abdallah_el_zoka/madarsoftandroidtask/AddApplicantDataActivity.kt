package com.dev_abdallah_el_zoka.madarsoftandroidtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dev_abdallah_el_zoka.madarsoftandroidtask.databinding.ActivityAddApplicantDataBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class AddApplicantDataActivity : AppCompatActivity() {
    private lateinit var activityAddApplicantDataBinding: ActivityAddApplicantDataBinding

    @InternalCoroutinesApi
    private val addApplicantDataViewModel: AddApplicantDataViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddApplicantDataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_applicant_data)
        activityAddApplicantDataBinding.submitButton.setOnClickListener {
            addApplicantDataViewModel.validateUserInput(
                name = activityAddApplicantDataBinding.nameEditText.text.toString(),
                jobTitle = activityAddApplicantDataBinding.jobEditText.text.toString(),
                age = activityAddApplicantDataBinding.ageEditText.text.toString(),
                gender = if (activityAddApplicantDataBinding.genderSpinner.selectedItemPosition == 0) "Male" else "Female"
            )
        }
        activityAddApplicantDataBinding.showSavedApplicantsButton.setOnClickListener {
            addApplicantDataViewModel.navigator?.navigateToShowApplicantsDetailsActivity()
        }
    }
}