package com.katy.beneficiaries.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.katy.beneficiaries.R
import com.katy.beneficiaries.databinding.FragmentMainBinding
import com.katy.beneficiaries.di.AppComponent
import com.katy.beneficiaries.handler.DataFetchResultHandler
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.ui.adapter.BeneficiaryAdapter
import com.katy.beneficiaries.ui.adapter.CardDataWrapper

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private val stringUtils = AppComponent.getStringUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        if (!viewModel.beneficiaryData.isInitialized) {
            viewModel.initiateDataFetch(requireContext(), object :DataFetchResultHandler{
                override fun missingDataCallback() {
                    showErrorSnackbar(getString(R.string.imcomplete_data_message))
                }
                override fun noDataCallback() {
                    showErrorSnackbar(getString(R.string.no_data_message))
                }

            })
        }
    }

    private fun showErrorSnackbar(message:String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setupObservers() {
        viewModel.beneficiaryData.observe(viewLifecycleOwner) {
            setUpRecycler(it)
        }
    }

    private fun setUpRecycler(data: List<CardDataWrapper<Beneficiary>>) {
        val beneficiaryRecyclerView = binding.beneficiaryRecyclerView
        beneficiaryRecyclerView.layoutManager = LinearLayoutManager(activity)
        beneficiaryRecyclerView.adapter = BeneficiaryAdapter(data, stringUtils)
    }


}