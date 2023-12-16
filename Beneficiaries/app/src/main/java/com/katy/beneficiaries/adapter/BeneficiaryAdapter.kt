package com.katy.beneficiaries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        displayIfExists(holder.binding.beneTypeText, beneficiary.beneType)
        displayIfExists(holder.binding.designationText, beneficiary.designation?.toLocalizedString(context))

    }

    internal fun displayIfExists(textView: TextView, text: String?) {
        if (text != null) {
            textView.text = text
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }

    class ViewHolder(val binding: BeneficiaryCardBinding) : RecyclerView.ViewHolder(binding.root)
}
