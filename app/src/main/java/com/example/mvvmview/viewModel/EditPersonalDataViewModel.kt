package com.example.mvvmview.viewModel

import android.os.Parcelable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class EditPersonalDataViewModel @Inject constructor() : ViewModel() {

    val incomeLD = MutableLiveData<String>()
    var validationMessage = MutableLiveData<String>()
    private val _validationMessage = MutableLiveData(validationMessage.value)


    private val typeOfContractList =
        arrayOf("Wybierz:", "Umowa o pracę", "Umowa zlecenie", "Umowa o dzieło", "B2B")
    private var _typeOfContract = MutableLiveData(typeOfContractList)
    val typeOfContractLD: LiveData<Array<String>> = _typeOfContract

    val spinnerTypeOfContractLD = MutableLiveData(0)

    fun typeOfContractSelected(): Boolean {
        val selectedTypeOfContractIndex = spinnerTypeOfContractLD.value ?: 0
        val typeOfContractForSelection = _typeOfContract.value?.get(selectedTypeOfContractIndex)
        return if (typeOfContractForSelection == "Umowa o pracę") {
            return true
        } else {
            false
        }
    }

    val checkBoxLD = MutableLiveData<Boolean>(false)

    private val maritalStatusList = arrayOf(
        "Wybierz:",
        "panna",
        "kawaler",
        "zamężna",
        "żonaty",
        "rozwiedziona",
        "rozwiedziony",
        "wdowa",
        "wdowiec"
    )
    private var _maritalStatus = MutableLiveData(maritalStatusList)
    val maritalStatusLD: LiveData<Array<String>> = _maritalStatus

    val spinnerMaritalStatusLD = MutableLiveData(0)

    fun isSpouseStatusSelected(): Boolean {
        val selectedMaritalStatusIndex = spinnerMaritalStatusLD.value ?: 0
        val spouseStatusForSelection = _maritalStatus.value?.get(selectedMaritalStatusIndex)
        return spouseStatusForSelection == "zamężna" || spouseStatusForSelection == "żonaty"
    }

    val spousesIncomeLD = MutableLiveData<String>()

    fun getDataForIntent(): ExtraDataConfig {
        return ExtraDataConfig(
            income = incomeLD.value,
            typeOfContract = spinnerTypeOfContractLD.value?.let { typeOfContractList[it] }, // tu moze byc blad
            isTimelessContract = checkBoxLD.value,
            maritalStatus = spinnerMaritalStatusLD.value?.let { maritalStatusList[it] }, // tu tez
            spousesIncome = spousesIncomeLD.value
        )
    }

    fun hasValidationError(): Boolean {
        if (incomeLD.value.isNullOrBlank()) {
            validationMessage.value = "Uzupełnij pole: Dochód"
            return true
        } else if (spinnerTypeOfContractLD.value == 0) {
            validationMessage.value = "Zaznacz pole: Rodzaj umowy"
            return true
        } else if (spinnerMaritalStatusLD.value == 0) {
            validationMessage.value = "Zaznacz pole: Stan cywilny"
            return true
        } else if ((spinnerMaritalStatusLD.value == 3 || spinnerMaritalStatusLD.value == 4) && (spousesIncomeLD.value.isNullOrBlank())) {
            validationMessage.value = "Uzupełnij pole: Dochód współmałżonka"
            return true
        } else return false
    }

}

@Parcelize
data class ExtraDataConfig(
    val income: String?,
    val typeOfContract: String?,
    val isTimelessContract: Boolean?,
    val maritalStatus: String?,
    val spousesIncome: String?,

    ) : Parcelable

object PersonalDataConst {
    const val EXTRA_DATA = "ExtraData" // rozkminic po co to
}