package com.katy.beneficiaries.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katy.beneficiaries.R
import com.katy.beneficiaries.databinding.BeneficiaryCardBinding
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.model.toLocalizedString
import com.katy.beneficiaries.util.Constants

class BeneficiaryAdapter(private val data: List<Beneficiary>) :
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
    }


    /*
    If there is data for both designation and beneType they are displayed as
    <designation> Beneficiary, <beneType>. If only one has data it is displayed alone.
    There is data for neither they are not displayed.
     */
    internal fun displayDesignationAndBeneType(
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

    class ViewHolder(val binding: BeneficiaryCardBinding) : RecyclerView.ViewHolder(binding.root)
}
