package com.example.albertsome_task.ui.home

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
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
        val btnFetch: AppCompatButton = view.findViewById<AppCompatButton>(R.id.btnFetch)

        btnFetch.setOnClickListener{
            if(inputNumber.text.toString().isEmpty() &&
                inputNumber.text.toString().toInt() == 0){
                Toast.makeText(requireContext(),"Please enter valid number", Toast.LENGTH_SHORT).show()
            }else{
                fetchUser.invoke(inputNumber.text.toString().toInt())
                dismiss()
            }
        }

        return alertDialog.create()
    }
}