package com.simpson.game

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    private lateinit var leftButton: ImageButton
    private lateinit var rightButton: ImageButton
    private lateinit var upButton: ImageButton
    private lateinit var actionButton: ImageButton
    private lateinit var skinButton: ImageButton

    private var selectedSkin = TouchSkin.CLASSIC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = findViewById(R.id.gameView)
        leftButton = findViewById(R.id.leftButton)
        rightButton = findViewById(R.id.rightButton)
        upButton = findViewById(R.id.upButton)
        actionButton = findViewById(R.id.actionButton)
        skinButton = findViewById(R.id.skinButton)

        setupDirectionalButton(leftButton, -1, 0)
        setupDirectionalButton(rightButton, 1, 0)
        setupDirectionalButton(upButton, 0, -1)
        setupActionButton(actionButton)

        skinButton.setOnClickListener { showSkinChooser() }
        applySkin(selectedSkin)
    }

    private fun setupDirectionalButton(button: ImageButton, dx: Int, dy: Int) {
        button.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    gameView.setMoveVector(dx, dy)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    gameView.setMoveVector(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupActionButton(button: ImageButton) {
        button.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    gameView.performAction()
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> true
                else -> false
            }
        }
    }

    private fun showSkinChooser() {
        val skins = listOf(TouchSkin.CLASSIC, TouchSkin.NEON, TouchSkin.ARCADE)
        val labels = skins.map { it.label }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle(R.string.skin_title)
            .setSingleChoiceItems(labels, skins.indexOf(selectedSkin)) { dialog, which ->
                selectedSkin = skins[which]
                applySkin(selectedSkin)
                dialog.dismiss()
            }
            .show()
    }

    private fun applySkin(skin: TouchSkin) {
        leftButton.setImageResource(skin.left)
        rightButton.setImageResource(skin.right)
        upButton.setImageResource(skin.up)
        actionButton.setImageResource(skin.action)
        skinButton.setImageResource(skin.action)
    }
}
