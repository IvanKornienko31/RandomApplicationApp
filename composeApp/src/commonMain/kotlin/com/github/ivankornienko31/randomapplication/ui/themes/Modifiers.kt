package com.github.ivankornienko31.randomapplication.ui.themes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

/**
 * TODO: в дальнейшем добавить документацию для модификаторов объектов
 * @author Иван Корниенко
 */

class CustomModifiers {
    companion object {
        val scaffoldModifier: Modifier = Modifier.fillMaxSize()
        val flexModifier: Modifier = scaffoldModifier
        val constraintModifier: (PaddingValues) -> Modifier = { innerPadding ->
            Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(CustomDimens.paddingFromEdges)
        }
        val inputModifier: Modifier = Modifier.fillMaxWidth()
        val buttonModifier: Modifier =
            Modifier.fillMaxWidth().heightIn(min = CustomDimens.buttonHeight)
        val pictureModifier: Modifier = Modifier.clip(CustomShapes.pictureShape)
            .size(CustomDimens.pictureSize)
        val dividerModifier: Modifier =
            Modifier.width(CustomDimens.dividerLength).clip(CustomShapes.dividerShape)
    }
}
