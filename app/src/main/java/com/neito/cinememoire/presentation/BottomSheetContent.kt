package com.neito.cinememoire.presentation

sealed class BottomSheetContent {
    data object SettingContent : BottomSheetContent()
    data object CreateContent : BottomSheetContent()
}