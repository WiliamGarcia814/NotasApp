package com.whgarcia.notas.state

import androidx.compose.ui.graphics.Color


data class NotasState(
    val title: String = "",
    val content: String = "",
    val create_date: String = "",
    val edit_date: String = "",
    val edit: Boolean = false,
    val selectedColor: Color = Color.Yellow,
    val showTitleError: Boolean = false,
    val showContentError: Boolean = false,
    val showDeleteConfirmation: Boolean = false,
    val showRecoverConfirmation: Boolean = false
)
