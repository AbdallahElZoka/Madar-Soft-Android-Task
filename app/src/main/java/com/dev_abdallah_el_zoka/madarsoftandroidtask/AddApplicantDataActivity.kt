package com.dev_abdallah_el_zoka.madarsoftandroidtask

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.dev_abdallah_el_zoka.madarsoftandroidtask.databinding.ActivityAddApplicantDataBinding
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
                        val alertDialogBuilder: AlertDialog.Builder =
                            AlertDialog.Builder(this@AddApplicantDataActivity)
                                .setTitle(getString(R.string.applicant_added_successfully))
                                .setPositiveButton(
                                    getString(R.string.ok)
                                ) { dialog, which ->
                                    dialog.dismiss()


                                }
                        alertDialogBuilder.create()
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