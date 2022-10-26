package com.example.mvvmview.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.mvvmview.R
import com.example.mvvmview.databinding.EditPersonalDataBinding
import com.example.mvvmview.databinding.PersonalDataBinding
import com.example.mvvmview.viewModel.EditPersonalDataViewModel
import com.example.mvvmview.viewModel.PersonalDataConst
import com.example.mvvmview.viewModel.PersonalDataViewModel

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var binding: PersonalDataBinding
    private val viewModel: PersonalDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val incomeObserverPersonalVM = Observer<String> { value ->
            binding.incomeSecondAct.text = value
        }
        viewModel.personalDateLDPersonalVM.observe(this, incomeObserverPersonalVM)
        viewModel.personalDateLDPersonalVM.value

        val typeOfContractObserverPersonalVM = Observer<String> { value ->
            binding.typeOfContractSecondAct.text = value
        }
        viewModel.typeOfContractLDPersonalVM.observe(this, typeOfContractObserverPersonalVM)

        val isTimelessContractObserverPersonalVM = Observer<Boolean> { value ->
            if (viewModel.isTimelessContractLDPersonalVM.value == true) {
                binding.contractTimeSecondAct.visibility = View.VISIBLE
            } else binding.contractTimeSecondAct.visibility = View.GONE

        }
        viewModel.isTimelessContractLDPersonalVM.observe(this, isTimelessContractObserverPersonalVM)

        val maritalStatusObserverPersonalVM = Observer<String> { value ->
            binding.maritalStatusSecondAct.text = value
        }
        viewModel.maritalStatusLDPersonalVM.observe(this, maritalStatusObserverPersonalVM)
        viewModel.maritalStatusLDPersonalVM.value

        val spousesIncomeObserverPersonalVM = Observer<String> { value ->
            binding.spousesIncomeSecondAct.text = value
            if (viewModel.maritalStatusLDPersonalVM.value == "żonaty" || viewModel.maritalStatusLDPersonalVM.value == "zamężna") {
                binding.spousesIncomeSecondActTW.visibility = View.VISIBLE
                binding.spousesIncomeSecondAct.visibility = View.VISIBLE
            } else {
                binding.spousesIncomeSecondActTW.visibility = View.GONE
                binding.spousesIncomeSecondAct.visibility = View.GONE
            }


        }

        viewModel.spousesIncomeLDPersonalVM.observe(this, spousesIncomeObserverPersonalVM)

        binding.sendBT.setOnClickListener {
            val sendEmailIntent = Intent(Intent.ACTION_SENDTO)

            sendEmailIntent.data = Uri.parse("mailto:")
            sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, "edyta.pyka.1994@gmail.com")
            sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "TEST TOPIC")
            sendEmailIntent.putExtra(Intent.EXTRA_TEXT, viewModel.getProperString())
            sendEmailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(Intent.createChooser(sendEmailIntent, "Send email"))

        }

    }
}
