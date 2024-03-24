package com.example.demomarvel.ui.share.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demomarvel.ui.share.theme.md_theme_light_onPrimary
import com.example.demomarvel.ui.share.theme.md_theme_light_primary

@Composable
fun CustomButton(text: String, onClick: ()-> Unit, isPrimary: Boolean, modifier: Modifier = Modifier, enabled: Boolean = true) = if (isPrimary) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = md_theme_light_primary,
            contentColor = md_theme_light_onPrimary),
        modifier = modifier
            .padding(20.dp, 0.dp)
            .fillMaxWidth(),
        enabled = enabled
    ) {
        Text(text = text)
    }
} else {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(23.dp),
        border = BorderStroke(1.dp, md_theme_light_primary),
        colors = ButtonDefaults.buttonColors(
            containerColor = md_theme_light_onPrimary,
            contentColor = md_theme_light_primary,
        ),

        modifier = modifier
            .padding(20.dp, 10.dp)
            .fillMaxWidth(),
        enabled = enabled


    ) {
        Text(text = text)
    }
}