package com.example.weighttracker

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.weighttracker.database.WeightEntry
import java.text.SimpleDateFormat


fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy")
        .format(systemTime).toString()
}

fun formatEntries(entries: List<WeightEntry>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        entries.forEach {
            append("<br>")
            append(resources.getString(R.string.date))
            append("\t${convertLongToDateString(it.currentTime)}<br>")
            append(resources.getString(R.string.weight))
            append("\t${it.weight}")
            append("<br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
