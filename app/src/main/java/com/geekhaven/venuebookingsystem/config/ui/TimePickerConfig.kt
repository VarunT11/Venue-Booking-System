package com.geekhaven.venuebookingsystem.config.ui

import java.util.Calendar

class TimePickerConfig(
    val title: String,
    val hour: Int,
    val minute: Int,
    val onSelect: (Calendar) -> Unit,
)
