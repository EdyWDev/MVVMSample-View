package com.example.mvvmview.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mvvmview.databinding.EditPersonalDataBinding
import com.example.mvvmview.viewModel.EditPersonalDataViewModel
import com.example.mvvmview.viewModel.PersonalDataConst
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditPersonalDataActivity : AppCompatActivity() {

    private val viewModel: EditPersonalDataViewModel by viewModels()

    private lateinit var binding: EditPersonalDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val incomeETObserver = Observer<String> { value ->
            binding.incomeET.setText(value)
        }
        binding.incomeET.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.incomeET.removeTextChangedListener(this)

                s?.let {

                    if (viewModel.incomeLD.value != binding.incomeET.text.toString()) {
                        viewModel.incomeLD.value = it.toString()
                    }
                }
                binding.incomeET.addTextChangedListener(this)
                binding.incomeET.setSelection(binding.incomeET.length())

            }

        })

        viewModel.incomeLD.observe(this, incomeETObserver)


        val spinnerTypeOfContractObserver = Observer<Int> { value ->
            binding.spinnerTypeOfContract.setSelection(value)
        }
        viewModel.spinnerTypeOfContractLD.observe(this, spinnerTypeOfContractObserver)


        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.typeOfContractLD.value?.toList() ?: emptyList()
        )
        binding.spinnerTypeOfContract.adapter = arrayAdapter
        binding.spinnerTypeOfContract.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                viewModel.spinnerTypeOfContractLD.value = position
                if (viewModel.typeOfContractSelected()) {
                    binding.checkBox.visibility = View.VISIBLE
                } else {
                    binding.checkBox.visibility = View.GONE
                }
            }

        }

        // to dziala tylko brakuje Ci update wartosci vm na podstawie tego co uzytkownik zmienil. Dopiac onCheckedChangedListener
        val checkBoxObserver = Observer<Boolean> { value ->
            binding.checkBox.isChecked = value
        }

        viewModel.checkBoxLD.observe(this, checkBoxObserver)
        binding.checkBox.setOnCheckedChangeListener { _, b ->
            viewModel.checkBoxLD.value = b
        }

        val arrayAdapterMaritalStatus = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.maritalStatusLD.value?.toList() ?: emptyList()
        )
        binding.spinnerMaritalStatus.adapter = arrayAdapterMaritalStatus
        binding.spinnerMaritalStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {

                    viewModel.spinnerMaritalStatusLD.value = position
                    if (viewModel.isSpouseStatusSelected()) {
                        binding.spousesIncomeTV.visibility = View.VISIBLE
                        binding.spousesIncomeET.visibility = View.VISIBLE
                    } else {
                        binding.spousesIncomeTV.visibility = View.GONE
                        binding.spousesIncomeET.visibility = View.GONE
                    }
                }


            }
        val spinnerMaritalStatusObserver = Observer<Int> { value ->
            binding.spinnerMaritalStatus.setSelection(value)
        }
        viewModel.spinnerMaritalStatusLD.observe(this, spinnerMaritalStatusObserver)

        val spousesIncomeObserver = Observer<String> {
            binding.spousesIncomeET
        }
        binding.spousesIncomeET.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.spousesIncomeET.removeTextChangedListener(this)

                s?.let {

                    if (viewModel.spousesIncomeLD.value != binding.incomeET.text.toString()) {
                        viewModel.spousesIncomeLD.value = it.toString()
                    }
                }
                binding.spousesIncomeET.addTextChangedListener(this)
                binding.spousesIncomeET.setSelection(binding.spousesIncomeET.length())

            }

        })
        viewModel.spousesIncomeLD.observe(this, spousesIncomeObserver)

        binding.nextBT.setOnClickListener {
           if(!viewModel.hasValidationError()){
               val intent = Intent(this, PersonalDataActivity::class.java)
               intent.putExtra(PersonalDataConst.EXTRA_DATA, viewModel.getDataForIntent())
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
               startActivity(intent)

           }
        }

        val validationMessageObserver = Observer<String>{value ->
            Snackbar.make(binding.root, value, Snackbar.LENGTH_LONG).show()
        }
        viewModel.validationMessage.observe(this, validationMessageObserver)

    }
}
