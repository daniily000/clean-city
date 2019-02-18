package ru.konighack2019.cleancity.presentation.results.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.item_report.view.*
import kotlinx.android.synthetic.main.item_report_expanded.view.*
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.BuildConfig
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.dateToReadable
import ru.konighack2019.cleancity.model.KSEntry

class KSEntryHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private var expanded = false

    fun bind(ksEntry: KSEntry) {
        view.tv_report_address.text = ksEntry.address
        view.tv_report_date.text = dateToReadable(ksEntry.createdAt.toLong())
        when (ksEntry.status) {
            "1" -> view.chip_report_status.text = "обрабатывается"
            "2" -> view.chip_report_status.text = "на проверке"
            "3" -> view.chip_report_status.text = "ответ готов"
        }
        view.tv_report_subject.text = ksEntry.subject
        view.tv_report_description.text = ksEntry.description
        view.tv_report_comment.text = ksEntry.comment

        spawnPhotosInParent(ksEntry.photo, view.report_layout_photo)
        spawnPhotosInParent(ksEntry.photoAdmin, view.report_layout_photo_admin)

        view.setOnClickListener {
            when (expanded) {
                true -> {
                    view.iv_expand.setImageDrawable(AppDelegate.applicationContext().getDrawable(R.drawable.ic_expand))
                    view.report_expanded.visibility = View.GONE
                }
                false -> {
                    view.iv_expand.setImageDrawable(AppDelegate.applicationContext().getDrawable(R.drawable.ic_collapse))
                    view.report_expanded.visibility = View.VISIBLE
                }
            }
            expanded = !expanded
        }
    }

    private fun dipsToPixels(sizeInDp: Float): Int =
        (sizeInDp * AppDelegate.applicationContext().resources.displayMetrics.density + 0.5f).toInt()


    private fun spawnPhotosInParent(list: List<String>, parent: ViewGroup) {
        for (photo in list) {
            val imageViewParams = ViewGroup.LayoutParams(dipsToPixels(128f), dipsToPixels(192f))
            val imageView = ImageView(AppDelegate.applicationContext()).also { it.layoutParams = imageViewParams }
            imageView.layoutParams = imageViewParams
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(view).load(BuildConfig.API_URL + photo).into(imageView)
            parent.addView(imageView)
        }
    }
}

