package com.example.albertsome_task.ui.home

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import com.example.albertsome_task.R

class InputUserRecordFragment(private val fetchUser:(Int) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.layout_records,null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Cancel"){dialog,_ ->
                dialog.dismiss()
            }

        val inputNumber: AppCompatEditText = view.findViewById<AppCompatEditText>(R.id.inputNumber)
        val textError: AppCompatTextView = view.findViewById<AppCompatTextView>(R.id.textError)
        val btnFetch: AppCompatButton = view.findViewById<AppCompatButton>(R.id.btnFetch)
        resetErrorText(inputNumber,textError)

        btnFetch.setOnClickListener{
            if(inputNumber.text.toString().isEmpty()){
                textError.visibility  = View.VISIBLE
                textError.setText("Please enter valid number to fetch records!")
            }else{
                fetchUser.invoke(inputNumber.text.toString().toInt())
                dismiss()
            }
        }

        return alertDialog.create()
    }

    private fun resetErrorText(inputNumber: AppCompatEditText,textError: AppCompatTextView){
        inputNumber.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                textError.visibility  = View.GONE
                textError.text = ""
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
}