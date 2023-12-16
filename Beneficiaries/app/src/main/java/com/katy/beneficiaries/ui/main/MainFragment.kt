package com.katy.beneficiaries.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.katy.beneficiaries.R
import com.katy.beneficiaries.adapter.BeneficiaryAdapter
import com.katy.beneficiaries.databinding.FragmentMainBinding
import com.katy.beneficiaries.model.Beneficiary

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

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
        viewModel.initiateDataFetch(requireContext(), {}, ::setUpRecycler)
    }

    private fun setUpRecycler(data: List<Beneficiary>) {
        val forecastRecyclerView = binding.beneficiaryRecyclerView
        forecastRecyclerView.layoutManager = LinearLayoutManager(activity)
        forecastRecyclerView.adapter = BeneficiaryAdapter(data)
    }




}