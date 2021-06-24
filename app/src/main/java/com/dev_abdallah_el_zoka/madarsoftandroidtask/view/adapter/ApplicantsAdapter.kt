package com.dev_abdallah_el_zoka.madarsoftandroidtask.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev_abdallah_el_zoka.madarsoftandroidtask.R
import com.dev_abdallah_el_zoka.madarsoftandroidtask.databinding.ApplicantItemUiBinding
import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo.ApplicantModel

class ApplicantsAdapter(var applicants: List<ApplicantModel>?) :
    RecyclerView.Adapter<ApplicantsAdapter.ApplicantsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantsViewHolder {
        return ApplicantsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.applicant_item_ui,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ApplicantsViewHolder, position: Int) {
        holder.applicantItemUiBinding.applicantModel = applicants?.get(position)
    }

    override fun getItemCount(): Int {
        return applicants?.size ?: 0
    }

    fun updateData(applicants: List<ApplicantModel>?) {
        this.applicants = applicants
        notifyDataSetChanged()
    }

    inner class ApplicantsViewHolder(val applicantItemUiBinding: ApplicantItemUiBinding) :
        RecyclerView.ViewHolder(applicantItemUiBinding.root)

}