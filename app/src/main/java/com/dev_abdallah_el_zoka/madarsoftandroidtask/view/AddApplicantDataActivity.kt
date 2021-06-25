package com.dev_abdallah_el_zoka.madarsoftandroidtask.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.dev_abdallah_el_zoka.madarsoftandroidtask.*
import com.dev_abdallah_el_zoka.madarsoftandroidtask.databinding.ActivityAddApplicantDataBinding
import com.dev_abdallah_el_zoka.madarsoftandroidtask.state.SaveApplicantsState
import com.dev_abdallah_el_zoka.madarsoftandroidtask.view_model.AddApplicantDataViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddApplicantDataActivity : AppCompatActivity(), Navigator {
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
        addApplicantDataViewModel.navigator = this
        activityAddApplicantDataBinding.viewModel = addApplicantDataViewModel
        lifecycleScope.launch {
            addApplicantDataViewModel.jobTitleErrorStateFlow.collect {
                if (it.blankError == true) {
                    Log.e("MyTag", "Job Title Has Blank Error ")
                    activityAddApplicantDataBinding.jobEditText.error =
                        "Job title field is required"
                    activityAddApplicantDataBinding.jobEditTextLayout.endIconMode =
                        TextInputLayout.END_ICON_NONE
                } else if (it.containSymbolsError == true) {
                    Log.e("MyTag", "Name Contains Symbols")
                    activityAddApplicantDataBinding.jobEditText.error =
                        "Job Title can't contain numbers or special characters"
                    activityAddApplicantDataBinding.jobEditTextLayout.endIconMode =
                        TextInputLayout.END_ICON_NONE
                } else {
                    Log.e("MyTag", "Job Title Has NO Blank Error ")
                    activityAddApplicantDataBinding.jobEditText.error = null
                    activityAddApplicantDataBinding.jobEditTextLayout.endIconMode =
                        TextInputLayout.END_ICON_CLEAR_TEXT
                }
            }
        }
        lifecycleScope.launch {
            addApplicantDataViewModel.ageErrorStateFlow.collect {
                if (it.blankError == true) {
                    Log.e("MyTag", "Age Has Blank Error ")
                    activityAddApplicantDataBinding.ageEditText.error =
                        "Required and enter valid age"
                    activityAddApplicantDataBinding.ageEditTextLayout.endIconMode =
                        TextInputLayout.END_ICON_NONE
                } else {
                    Log.e("MyTag", "Age Has NO Blank Error ")
                    activityAddApplicantDataBinding.ageEditText.error = null
                    activityAddApplicantDataBinding.ageEditTextLayout.endIconMode =
                        TextInputLayout.END_ICON_CLEAR_TEXT
                }
            }
        }
        lifecycleScope.launch {
            addApplicantDataViewModel.nameErrorStateFlow.collect {
                if (it.blankError == true) {
                    Log.e("MyTag", "Name Has Blank Error ")
                    activityAddApplicantDataBinding.nameEditText.error = "Name field is required"
                } else if (it.containSymbolsError == true) {
                    Log.e("MyTag", "Name Contains Symbols")
                    activityAddApplicantDataBinding.nameEditText.error =
                        "Name can't contain numbers or special characters"
                    activityAddApplicantDataBinding.nameEditTextLayout.endIconMode =
                        TextInputLayout.END_ICON_NONE
                } else {
                    Log.e("MyTag", "Name Has NO Blank Error ")
                    activityAddApplicantDataBinding.nameEditText.error = null
                    activityAddApplicantDataBinding.nameEditTextLayout.endIconMode =
                        TextInputLayout.END_ICON_CLEAR_TEXT
                }
            }
        }
        lifecycleScope.launch {
            addApplicantDataViewModel.applicantsState.collect {
                when (it) {
                    is SaveApplicantsState.Idle -> {
                        activityAddApplicantDataBinding.submitButton.text =
                            getString(R.string.submit)
                        activityAddApplicantDataBinding.loadingProgressBar.visibility = View.GONE
                    }
                    is SaveApplicantsState.Loading -> {
                        activityAddApplicantDataBinding.submitButton.text = "  "
                        activityAddApplicantDataBinding.loadingProgressBar.visibility = View.VISIBLE
                    }
                    is SaveApplicantsState.Error -> {
                        activityAddApplicantDataBinding.submitButton.text =
                            getString(R.string.submit)
                        activityAddApplicantDataBinding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@AddApplicantDataActivity, it.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is SaveApplicantsState.Done -> {
                        activityAddApplicantDataBinding.submitButton.text =
                            getString(R.string.submit)
                        activityAddApplicantDataBinding.loadingProgressBar.visibility = View.GONE
                        val alertDialogBuilder: AlertDialog.Builder =
                            AlertDialog.Builder(this@AddApplicantDataActivity)
                                .setTitle(getString(R.string.applicant_added_successfully))
                                .setPositiveButton(
                                    getString(R.string.ok)
                                ) { dialog, which ->
                                    dialog.dismiss()


                                }
                        alertDialogBuilder.create().show()
                    }
                }
            }

        }
    }

    override fun navigateToShowApplicantsDetailsActivity() {
        startActivity(
            Intent(
                this@AddApplicantDataActivity,
                ShowApplicantsDetailsActivity::class.java
            )
        )
    }
}