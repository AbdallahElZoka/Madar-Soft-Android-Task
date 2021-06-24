package com.dev_abdallah_el_zoka.madarsoftandroidtask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_abdallah_el_zoka.madarsoftandroidtask.intent.FetchApplicantsIntent
import com.dev_abdallah_el_zoka.madarsoftandroidtask.state.FetchApplicantsState
import com.dev_abdallah_el_zoka.madarsoftandroidtask.R
import com.dev_abdallah_el_zoka.madarsoftandroidtask.view_model.ShowApplicantsDetailsViewModel
import com.dev_abdallah_el_zoka.madarsoftandroidtask.databinding.ActivityShowApplicantsDetailsBinding
import com.dev_abdallah_el_zoka.madarsoftandroidtask.view.adapter.ApplicantsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowApplicantsDetailsActivity : AppCompatActivity() {

    private lateinit var activityShowApplicantsDetailsActivity: ActivityShowApplicantsDetailsBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var applicantsAdapter: ApplicantsAdapter

    @InternalCoroutinesApi
    private val showApplicantsDetailsViewModel: ShowApplicantsDetailsViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityShowApplicantsDetailsActivity =
            DataBindingUtil.setContentView(this, R.layout.activity_show_applicants_details)
        linearLayoutManager = LinearLayoutManager(this@ShowApplicantsDetailsActivity)
        applicantsAdapter = ApplicantsAdapter(null)
        lifecycleScope.launch {
            showApplicantsDetailsViewModel.userIntent.send(FetchApplicantsIntent.FetchApplicants)
            showApplicantsDetailsViewModel.applicantsState.collect {
                when (it) {
                    is FetchApplicantsState.Idle -> {
                        activityShowApplicantsDetailsActivity.loadingProgressBar.visibility =
                            View.VISIBLE
                    }
                    is FetchApplicantsState.Loading -> {
                        activityShowApplicantsDetailsActivity.loadingProgressBar.visibility =
                            View.VISIBLE
                    }
                    is FetchApplicantsState.Error -> {
                        activityShowApplicantsDetailsActivity.loadingProgressBar.visibility =
                            View.GONE
                        val alertDialogBuilder: AlertDialog.Builder =
                            AlertDialog.Builder(this@ShowApplicantsDetailsActivity)
                                .setTitle(it.error)
                                .setPositiveButton(
                                    getString(R.string.ok)
                                ) { dialog, which ->
                                    dialog.dismiss()


                                }
                        alertDialogBuilder.create().show()

                    }
                    is FetchApplicantsState.Applicants -> {
                        activityShowApplicantsDetailsActivity.loadingProgressBar.visibility =
                            View.GONE
                        applicantsAdapter.updateData(it.applicants)
                    }
                }
            }
        }
    }
}