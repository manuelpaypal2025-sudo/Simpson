package com.simpson.game

enum class TouchSkin(
    val label: String,
    val left: Int,
    val right: Int,
    val up: Int,
    val action: Int
) {
    CLASSIC("Classic", R.drawable.ic_button_left, R.drawable.ic_button_right, R.drawable.ic_button_up, R.drawable.ic_button_action),
    NEON("Neon", R.drawable.ic_button_left, R.drawable.ic_button_right, R.drawable.ic_button_up, R.drawable.ic_button_action),
    ARCADE("Arcade", R.drawable.ic_button_left, R.drawable.ic_button_right, R.drawable.ic_button_up, R.drawable.ic_button_action)
}
