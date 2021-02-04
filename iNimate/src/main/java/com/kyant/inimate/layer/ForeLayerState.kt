package com.kyant.inimate.layer

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.ui.unit.Constraints

@OptIn(ExperimentalMaterialApi::class)
typealias ForeLayerState = SwipeableState<Boolean>

@OptIn(ExperimentalMaterialApi::class)
fun ForeLayerState.progress(constraints: Constraints): Float =
    1f - (if (offset.value.isNaN()) 0f else offset.value) / constraints.maxHeight.toFloat()