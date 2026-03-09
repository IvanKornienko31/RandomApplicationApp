package com.github.ivankornienko31.stepikclientapplication.presentation.themes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape

/**
 * TODO: в дальнейшем добавить документацию для фигур
 * @author Иван Корниенко
 */

class CustomShapes {
    companion object {
        val unifiedShape: Shape = RoundedCornerShape(CustomDimens.inputCornerRadius)
        val pictureShape: Shape = RoundedCornerShape(CustomDimens.pictureCornerRadius)
        val dividerShape: Shape = RoundedCornerShape(percent = 50)
    }
}