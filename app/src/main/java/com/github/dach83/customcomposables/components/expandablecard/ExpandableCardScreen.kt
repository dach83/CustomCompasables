package com.github.dach83.customcomposables.components.expandablecard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.dach83.customcomposables.R

@Composable
fun ExpandableCardScreen() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.padding(16.dp)
    ) {
        ExpandableCard(
            title = "Title",
            modifier = Modifier.wrapContentHeight()

        ) {
            Text(text = stringResource(id = R.string.lorem_ipsum))
        }
    }
}
