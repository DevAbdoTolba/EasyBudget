package com.benoitletondor.easybudgetapp.view.settings.subviews

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.benoitletondor.easybudgetapp.R
import com.benoitletondor.easybudgetapp.helper.CurrencyHelper
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Context.showThreeDayRollingBudgetPickerDialog(
    currentBudgetLimit: Double,
    onBudgetLimitChanged: (Double) -> Unit,
) {
    val dialogView = EditText(this)
    dialogView.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    dialogView.setText(DecimalFormat("0.00").format(currentBudgetLimit))
    dialogView.hint = getString(R.string.setting_category_three_day_budget_dialog_hint)
    
    // Limit to 2 decimal places
    val symbols = DecimalFormatSymbols(Locale.getDefault())
    val decimalSeparator = symbols.decimalSeparator.toString()
    dialogView.filters = arrayOf(
        InputFilter { source, start, end, dest, dstart, dend ->
            val builder = StringBuilder(dest)
            builder.replace(dstart, dend, source.subSequence(start, end).toString())
            val resultString = builder.toString()
            
            if (resultString.isEmpty()) return@InputFilter null
            
            val indexOfDecimalPoint = resultString.indexOf(decimalSeparator)
            if (indexOfDecimalPoint < 0) return@InputFilter null
            
            if (resultString.length - indexOfDecimalPoint - 1 > 2) {
                return@InputFilter ""
            }
            
            null
        }
    )

    // Prevent the dialog from being dismissed when an invalid value is entered
    dialogView.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        
        override fun afterTextChanged(s: Editable) {
            try {
                val value = s.toString().toDouble()
                if (value <= 0) {
                    dialogView.error = "Value must be greater than 0"
                } else {
                    dialogView.error = null
                }
            } catch (e: Exception) {
                dialogView.error = "Invalid number"
            }
        }
    })

    AlertDialog.Builder(this)
        .setTitle(R.string.setting_category_three_day_budget_dialog_title)
        .setMessage(R.string.setting_category_three_day_budget_dialog_description)
        .setView(dialogView)
        .setPositiveButton(R.string.setting_category_three_day_budget_dialog_ok) { _, _ ->
            try {
                val inputText = dialogView.text.toString()
                // Check if the text is not empty and is a valid number
                if (inputText.isNotEmpty()) {
                    val budgetLimit = inputText.toDouble()
                    // Ensure the budget limit is greater than 0
                    if (budgetLimit > 0) {
                        onBudgetLimitChanged(budgetLimit)
                    } else {
                        Toast.makeText(
                            this,
                            "Please enter a value greater than 0",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    this,
                    "Invalid number format",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .setNegativeButton(R.string.setting_category_three_day_budget_dialog_cancel, null)
        .show()
}
