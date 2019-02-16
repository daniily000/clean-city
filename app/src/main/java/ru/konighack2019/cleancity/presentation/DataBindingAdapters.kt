package ru.konighack2019.cleancity.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamtwothree.kartasvalokapp.presentation.results.adapter.ReportsAdapter
import ru.konighack2019.cleancity.model.Point
import ru.konighack2019.cleancity.service.common.OperationState

//@BindingAdapter("formSubject")
//internal fun TextInputLayout.bindReportSubject(subject: String) {
//    this.form_field.hint = "Subject"
//    this.form_field.setText(subject, TextView.BufferType.EDITABLE)
//}
//
//@BindingAdapter("formDescrioption")
//internal fun TextInputLayout.bindReportDescription(description: String) {
//    this.form_field.hint = "Description"
//    this.form_field.setText(description, TextView.BufferType.EDITABLE)
//}
//
//@BindingAdapter("formAddress")
//internal fun TextInputLayout.bindReportAddress(address: String) {
//    this.form_field.hint = "Address"
//    this.form_field.setText(address, TextView.BufferType.EDITABLE)
//}
//
//@BindingAdapter("formErrors")
//internal fun TextInputLayout.bindFormError(err: String) {
//    this.form_field.error = err
//}

@BindingAdapter("operationResult")
internal fun TextView.bindValidationResult(operationResult: OperationState?) {
    this.text = operationResult.toString()
}

@BindingAdapter("items")
internal fun RecyclerView.bindFeedEntries(items: List<Point>?) {
    if (items != null && items.isNotEmpty()) {
        val adapter = adapter as ReportsAdapter
        adapter.items = items
        adapter.notifyDataSetChanged()
    }
}