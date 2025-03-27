package com.saika.multiversecheckup.presentation.views.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun LabeledDetail(text: String, contentDescription: String? = null) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription?.let { this.contentDescription = it }
            },
        textAlign = TextAlign.Start
    )
}
