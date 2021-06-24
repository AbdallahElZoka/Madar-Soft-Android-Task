package com.dev_abdallah_el_zoka.madarsoftandroidtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dev_abdallah_el_zoka.madarsoftandroidtask.databinding.ActivityAddApplicantDataBinding

class AddApplicantDataActivity : AppCompatActivity() {
    private lateinit var activityAddApplicantDataBinding: ActivityAddApplicantDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddApplicantDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_applicant_data)
        
    }
}