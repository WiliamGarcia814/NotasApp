package com.whgarcia.notas.state

data class NotasState(
    val title: String = "",
    val content: String = "",
    val create_date: String = "",
    val edit_date: String = "",
    val edit: Boolean = false,
    val color: Int = 0
)
