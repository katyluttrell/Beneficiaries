package com.katy.beneficiaries.adapter

import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katy.beneficiaries.R
import com.katy.beneficiaries.databinding.BeneficiaryCardBinding
import com.katy.beneficiaries.model.Address
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.model.toLocalizedString
import com.katy.beneficiaries.util.Constants
import com.katy.beneficiaries.util.StringUtils

class BeneficiaryAdapter(
    private val data: List<Beneficiary>,
    private val stringUtils: StringUtils
) :
    RecyclerView.Adapter<BeneficiaryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BeneficiaryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val beneficiary = data[position]
        holder.binding.beneficiaryName.text = beneficiary.middleName?.let {
            String.format(
                Constants.THREE_STRING_FORMAT,
                beneficiary.firstName,
                it,
                beneficiary.lastName
            )
        } ?: String.format(Constants.TWO_STRING_FORMAT, beneficiary.firstName, beneficiary.lastName)
        displayDesignationAndBeneType(
            context,
            holder.binding.designationText,
            beneficiary.designation?.toLocalizedString(context),
            holder.binding.beneTypeText,
            beneficiary.beneType
        )
        bindDetailView(holder.binding, beneficiary, context)
        holder.binding.chevron.setOnClickListener {
            showHideDetail(holder)
        }
    }

    private fun bindDetailView(
        binding: BeneficiaryCardBinding,
        beneficiary: Beneficiary,
        context: Context
    ) {
        showIfNotNull(
            binding.ssnText,
            beneficiary.ssn?.let { String.format(context.getString(R.string.ssn_display), it) }
        )
        showIfNotNull(
            binding.dobText,
            beneficiary.dateOfBirth?.let {
                String.format(
                    context.getString(R.string.date_of_birth_display),
                    stringUtils.createStringFromLocalDate(it, Constants.PRETTY_DATE_FORMAT)
                )
            }
        )
        displayAddressIfNotNull(binding, beneficiary.address)
    }

    private fun displayAddressIfNotNull(binding: BeneficiaryCardBinding, address: Address?) {

    }

    private fun showIfNotNull(textView: TextView, text: String?) {
        if (text != null) {
            textView.visibility = View.VISIBLE
            textView.text = text
        } else {
            textView.visibility = View.GONE
        }
    }


    /*
    If there is data for both designation and beneType they are displayed as
    <designation> Beneficiary, <beneType>. If only one has data it is displayed alone.
    There is data for neither they are not displayed.
     */
    private fun displayDesignationAndBeneType(
        context: Context,
        designationTextView: TextView,
        designationText: String?,
        beneTypeTextView: TextView,
        beneTypeText: String?
    ) {
        designationTextView.visibility = if (designationText != null) View.VISIBLE else View.GONE
        beneTypeTextView.visibility = if (beneTypeText != null) View.VISIBLE else View.GONE
        designationText?.let {
            designationTextView.text = if (beneTypeText != null)
                String.format(context.getString(R.string.designation_comma), it)
            else String.format(context.getString(R.string.designation), it)
        }
        beneTypeText?.let { beneTypeTextView.text = it }
    }

    private fun showHideDetail(holder: ViewHolder) {
        if (holder.isExpanded) {
            holder.isExpanded = false
            TransitionManager.beginDelayedTransition(holder.binding.beneficiaryCard, ChangeBounds())
            holder.binding.beneficiaryDetail.visibility = View.GONE
        } else {
            holder.isExpanded = true
            TransitionManager.beginDelayedTransition(holder.binding.beneficiaryCard, ChangeBounds())
            holder.binding.beneficiaryDetail.visibility = View.VISIBLE
        }
    }

    class ViewHolder(val binding: BeneficiaryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        var isExpanded = false
    }
}
