package com.neito.cinememoire.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BottomSheetViewModel : ViewModel() {
    var showBottomSheet by mutableStateOf(false)
    var bottomSheetContent by mutableStateOf<BottomSheetContent?>(null)
}