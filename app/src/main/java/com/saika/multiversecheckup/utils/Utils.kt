package com.saika.multiversecheckup.utils

import android.content.Context
import android.content.Intent
import com.saika.multiversecheckup.R
import com.saika.multiversecheckup.domain.model.Character
import java.text.SimpleDateFormat
import java.util.Locale


fun formatDate(dateString: String): String {
    //2017-11-30T14:28:54.596Z
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        "Unknown Date"
    }
}

fun shareImage(
    context: Context,
    character: Character
) {
    val shareText =
        context.getString(
            R.string.check_out_this_image_title_author_published,
            character.name,
            character.status,
            formatDate(character.created),
            character.image
        )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(intent, "Share via"))
}
