package com.github.ivankornienko31.stepikclientapplication.themes

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * TODO: в дальнейшем добавить документацию для типографики
 * @author Иван Корниенко
 */

object CustomFontSizes {
    val buttonFontSize: TextUnit = 24.sp
    val headerFontSize: TextUnit = 36.sp
    val mainFontSize: TextUnit = 18.sp
    val headerLetterSpacing: TextUnit = 2.sp
    val mainLineHeight: TextUnit = 20.sp
}

object CustomTextStyles {
    val headerStyle: TextStyle = TextStyle(
        fontSize = CustomFontSizes.headerFontSize,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.W700,
        letterSpacing = CustomFontSizes.headerLetterSpacing
    )
    val mainTextStyle: TextStyle = TextStyle(
        fontSize = CustomFontSizes.mainFontSize,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.W500,
        lineHeight = CustomFontSizes.mainLineHeight
    )
    val buttonFontStyle: TextStyle = TextStyle(
        fontSize = CustomFontSizes.buttonFontSize,
        fontWeight = FontWeight.W700
    )
}
