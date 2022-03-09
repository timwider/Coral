package com.example.coral.viewmodels

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coral.data.Member
import com.example.coral.utils.Constants
import com.example.coral.utils.Converters
import com.example.coral.utils.HistoryConverter

class MemberDetailsViewModel: ViewModel() {

    var formingMember = MutableLiveData<Member>()
    var selectedDate = MutableLiveData<String>()
    var memberNote = MutableLiveData<String?>()

    fun getMemberFromArgs(member: Member) {
        formingMember.value = member
        memberNote.value = formingMember.value!!.note
    }

    fun getCurrentMember() = formingMember.value

    fun getLastPaymentFromStringList(currentPayment: String): Pair<String, String> {
        val date = currentPayment.split(" ")[0]
        val sum = currentPayment.split(" ")[1]
        return Pair(date, sum)
    }

    // when adding new payment and last one goes to history
    fun grabMemberDataForDataRow(): String {
        val dateAndSum = formingMember.value!!.currentPayment!!
        val workouts = formingMember.value!!.workouts
        var dataRow = ""
        if (!workouts.isNullOrEmpty()) {
            dataRow = HistoryConverter().allVarsToDataRow(
                paymentDate = dateAndSum.split(" ")[0],
                paymentSum = dateAndSum.split(" ")[1],
                workouts = workouts
            )
        }
        return dataRow
    }

    fun datePickerListener(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val formattedMonth = Converters().datePickerMonthToStringNum(month)
            selectedDate.value = "$day.$formattedMonth.$year"
        }
    }

    fun updateMemberOnDateSet() {
        formingMember.value!!.apply {
            if (workouts == null) {
                workouts = mutableListOf(selectedDate.value!!)
            } else workouts!!.add(selectedDate.value!!)

            daysLeft -= 1
        }
    }

    fun providePhoneUri(): Uri = Uri.parse("tel:${formingMember.value!!.phone}")

    fun providePhoneForCalling() = formingMember.value!!.phone

    fun formatPhoneForWhatsapp(phone: String): String {
        return if (phone[0] == '8') {
            phone.replaceFirst("8", "+7")
        } else phone
    }

    fun editedMemberReceived(editedMember: Member) {
        formingMember.value = editedMember
        selectedDate.value = ""
    }

    fun provideMemberPhoneForWhatsapp(): String? {
        val phone = formingMember.value!!.phone
        return if (!phone.isNullOrEmpty()) {
            formatPhoneForWhatsapp(phone)
        } else null
    }

    fun submitNote(note: String) {
        if (note != memberNote.value) {
            memberNote.value = note
            formingMember.value!!.note = note
        }
    }

    fun onFragmentResult(editedMember: Member?) {
        if (editedMember.hashCode() != getCurrentMember().hashCode()) {

            // Check if there's new payment or not. If yes, grab all past data and send it to mainViewModel livedata
            if (editedMember!!.currentPayment != getCurrentMember()!!.currentPayment) {
                val dataRow = grabMemberDataForDataRow()

                if (editedMember.memberHistory == null) {
                    editedMember.memberHistory = mutableListOf(dataRow)
                } else editedMember.memberHistory!!.add(dataRow)
            }
            editedMemberReceived(editedMember)
        }
    }

    fun deleteDateAfterSubmitted() {
        selectedDate = MutableLiveData<String>()
    }
}