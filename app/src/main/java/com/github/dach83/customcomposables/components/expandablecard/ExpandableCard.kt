package com.github.dach83.customcomposables.components.expandablecard

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    iconVector: ImageVector = Icons.Default.ArrowDropDown,
    iconRotateEnabled: Boolean = true,
    expanded: Boolean = false,
    content: @Composable () -> Unit
) {
    var expandedState by remember { mutableStateOf(expanded) }
    val rotateState by animateFloatAsState(
        targetValue = if (expandedState && iconRotateEnabled) 180f else 0f
    )

    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = titleStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(9f)
                )
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = "Expand",
                        modifier = Modifier
                            .weight(1f)
                            .rotate(rotateState)
                    )
                }
            }
            AnimatedVisibility(
                visible = expandedState,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                content()
            }
        }
    }
}
