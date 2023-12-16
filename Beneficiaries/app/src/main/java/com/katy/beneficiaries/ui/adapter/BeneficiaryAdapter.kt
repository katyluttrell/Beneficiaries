package com.katy.beneficiaries.ui.adapter

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
    private val data: List<CardDataWrapper<Beneficiary>>,
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
        val dataAtPosition = data[position]
        val beneficiary = dataAtPosition.data
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
            dataAtPosition.isExpanded = !dataAtPosition.isExpanded
            showHideDetail(holder, dataAtPosition.isExpanded)
        }
        //Display initial state of expansion on UI
        showHideDetail(holder, dataAtPosition.isExpanded)
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
        if (address != null) {
            binding.addressLayout.visibility = View.VISIBLE
            showIfNotNull(binding.addressFirstLine, address.firstLineMailing)
            showIfNotNull(binding.addressScndLine, address.scndLineMailing)
            showIfNotNull(
                binding.cityStateZip,
                formatCityStateZip(address.city, address.stateCode, address.zipCode)
            )
            showIfNotNull(binding.country, address.country)
        } else {
            binding.addressLayout.visibility = View.GONE
        }
    }

    private fun formatCityStateZip(city: String?, state: String?, zip: String?): String? {
        return if (city != null && state != null && zip != null) {
            String.format(Constants.CITY_STATE_ZIP_FORMAT, city, state, zip)
        } else if (city != null && state != null) {
            String.format(Constants.CITY_STATE_FORMAT, city, state)
        } else if (city != null && zip != null) {
            String.format(Constants.TWO_STRING_FORMAT, city, zip)
        } else if (state != null && zip != null) {
            String.format(Constants.TWO_STRING_FORMAT, state, zip)
        } else {
            null
        }
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

    private fun showHideDetail(holder: ViewHolder, shouldShow: Boolean) {
        if (shouldShow) {
            TransitionManager.beginDelayedTransition(holder.binding.beneficiaryCard, ChangeBounds())
            holder.binding.beneficiaryDetail.visibility = View.VISIBLE
        } else {
            TransitionManager.beginDelayedTransition(holder.binding.beneficiaryCard, ChangeBounds())
            holder.binding.beneficiaryDetail.visibility = View.GONE
        }
    }

    inner class ViewHolder(val binding: BeneficiaryCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}