package com.github.ivankornienko31.stepikclientapplication.themes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape

/**
 * TODO: в дальнейшем добавить документацию для фигур
 * @author Иван Корниенко
 */

object CustomShapes {
    val unifiedShape: Shape = RoundedCornerShape(CustomDimens.inputCornerRadius)
    val pictureShape: Shape = RoundedCornerShape(CustomDimens.pictureCornerRadius)
    val dividerShape: Shape = RoundedCornerShape(percent = 50)
}