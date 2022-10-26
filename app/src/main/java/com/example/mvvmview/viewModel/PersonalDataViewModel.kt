package com.example.mvvmview.viewModel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mvvmview.ui.EditPersonalDataActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val extraData: ExtraDataConfig? = savedStateHandle[PersonalDataConst.EXTRA_DATA]


    val personalDateLDPersonalVM = MutableLiveData<String>(extraData?.income)
    val typeOfContractLDPersonalVM = MutableLiveData<String>(extraData?.typeOfContract)
    val isTimelessContractLDPersonalVM = MutableLiveData<Boolean>(extraData?.isTimelessContract)
    val maritalStatusLDPersonalVM = MutableLiveData<String>(extraData?.maritalStatus)
    val spousesIncomeLDPersonalVM = MutableLiveData<String>(extraData?.spousesIncome)

    fun getProperString(): String {
        var actualString = StringBuilder()
        if (!personalDateLDPersonalVM.value.isNullOrBlank()) {
            actualString.append("Dochód: " + personalDateLDPersonalVM.value)
        }
        if (!typeOfContractLDPersonalVM.value.isNullOrBlank()) {
            actualString.append("\n" + "Rodzaj umowy: " + typeOfContractLDPersonalVM.value!!)
        }
        if (isTimelessContractLDPersonalVM.value == true) {

            actualString.append("\n" + "Umowa na czas nieokreślony: TAK")
        }
        if (!maritalStatusLDPersonalVM.value.isNullOrBlank()) {
            actualString.append("\n" + "Stan cywilny: " + maritalStatusLDPersonalVM.value)
        }
        if (!extraData?.spousesIncome.isNullOrBlank()) {
            actualString.append("\n" + "Dochód współmałżonka: " + spousesIncomeLDPersonalVM.value)
        }
        return actualString.toString()
    }


}
