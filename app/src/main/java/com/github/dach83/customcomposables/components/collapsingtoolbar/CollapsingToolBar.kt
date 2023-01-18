package com.github.dach83.customcomposables.components.collapsingtoolbar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.github.dach83.customcomposables.R

private val headerHeight = 250.dp
private val toolbarHeight = 64.dp

private val toolbarGradient = Brush.horizontalGradient(
    listOf(Color(0xff026586), Color(0xff032C45))
)

@Composable
fun CollapsingToolBar() {
    val scroll: ScrollState = rememberScrollState(0)

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(scroll, headerHeightPx, toolbarHeightPx)
        Body(scroll)
        Toolbar(scroll, headerHeightPx, toolbarHeightPx)
        Title(scroll, headerHeightPx, toolbarHeightPx)
    }
}

@Composable
private fun Header(scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float) {
    val headerAlpha = 1 - (scroll.value / (headerHeightPx - toolbarHeightPx)).coerceIn(0f, 1f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(brush = toolbarGradient)
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f // Parallax effect
                alpha = headerAlpha
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_new_york),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                        startY = 3 * headerHeightPx / 4 // Gradient applied to wrap the title only
                    )
                )
        )
    }
}

@Composable
private fun Body(scroll: ScrollState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))
        repeat(5) {
            Text(
                text = stringResource(R.string.lorem_ipsum),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun Toolbar(scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    if (scroll.value >= toolbarBottom) {
        TopAppBar(
            modifier = Modifier
                .background(brush = toolbarGradient),
            navigationIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
            title = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun Title(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    var titleHeightPx by remember {
        mutableStateOf(0f)
    }
    var titleWidthPx by remember {
        mutableStateOf(0f)
    }
    val titleHeightDp = with(LocalDensity.current) { titleHeightPx.toDp() }
    val titleWidthDp = with(LocalDensity.current) { titleWidthPx.toDp() }

    val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
    val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

    val titleFontScaleStart = 1f
    val titleFontScaleStop = 0.66f

    val scaleXY = lerp(
        titleFontScaleStart.dp,
        titleFontScaleStop.dp,
        collapseFraction
    )

    val titleExtraStartPadding = titleWidthDp * (1 - scaleXY.value) / 2

    val titleYStart = headerHeight - titleHeightDp - 16.dp
    val titleYStop = toolbarHeight / 2 - titleHeightDp / 2
    val titleYMiddle = headerHeight / 2

    val titleXStart = 16.dp
    val titleXStop = 64.dp - titleExtraStartPadding
    val titleXMiddle = titleXStop * 5 / 4

    Text(
        text = "New York",
        color = Color.White,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .graphicsLayer {
                translationY =
                    bezier(titleYStart, titleYMiddle, titleYStop, collapseFraction).toPx()
                translationX =
                    bezier(titleXStart, titleXMiddle, titleXStop, collapseFraction).toPx()

                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            }
    )
}

fun bezier(start: Dp, middle: Dp, stop: Dp, fraction: Float): Dp {
    val first = lerp(start, middle, fraction)
    val second = lerp(middle, stop, fraction)
    return lerp(first, second, fraction)
}
