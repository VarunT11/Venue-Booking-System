package com.geekhaven.venuebookingsystem.config.ui

class DatePickerConfig(
    val title: String,
    val defaultSelection: Long,
    val onSelect: (Long) -> Unit,
)
