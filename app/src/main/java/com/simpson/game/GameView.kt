package com.simpson.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var moveX = 0f
    private var moveY = 0f
    private var velocityY = 0f
    private var playerX = 160f
    private var playerY = 300f
    private var groundY = 0f
    private var lastFrameMs = 0L

    private val playerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFB703")
    }
    private val accentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FB8500")
    }
    private val groundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#3C7D3C")
    }
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#0F172A")
    }

    fun setMoveVector(dx: Int, dy: Int) {
        moveX = dx.toFloat()
        moveY = dy.toFloat()
    }

    fun performAction() {
        if (playerY >= groundY - 120f) {
            velocityY = -22f
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lastFrameMs = SystemClock.uptimeMillis()
        postOnAnimation(frameRunnable)
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(frameRunnable)
        super.onDetachedFromWindow()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        groundY = h - 120f
        playerY = groundY - 120f
    }

    private val frameRunnable = object : Runnable {
        override fun run() {
            val now = SystemClock.uptimeMillis()
            if (lastFrameMs == 0L) {
                lastFrameMs = now
            }
            val delta = ((now - lastFrameMs) / 1000f).coerceAtLeast(0.016f)
            lastFrameMs = now

            playerX += moveX * 260f * delta
            playerY += moveY * 180f * delta
            playerY += velocityY * 60f * delta
            velocityY += 42f * delta * 60f

            val minX = 90f
            val maxX = width - 90f
            playerX = playerX.coerceIn(minX, maxX)

            val floorY = groundY - 120f
            if (playerY >= floorY) {
                playerY = floorY
                velocityY = 0f
            }

            if (playerY <= 80f) {
                playerY = 80f
                velocityY = 0f
            }

            invalidate()
            postOnAnimation(this)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

        val skyLine = height * 0.72f
        val hillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#1E3A5F")
        }
        canvas.drawRect(0f, skyLine, width.toFloat(), height.toFloat(), hillPaint)

        for (i in 0..5) {
            val left = i * (width / 6f)
            canvas.drawCircle(left + 40f, skyLine - 70f, 55f, accentPaint)
        }

        canvas.drawRect(0f, groundY, width.toFloat(), height.toFloat(), groundPaint)

        canvas.drawCircle(playerX, playerY, 42f, playerPaint)
        canvas.drawCircle(playerX - 12f, playerY - 10f, 8f, accentPaint)
        canvas.drawCircle(playerX + 12f, playerY - 10f, 8f, accentPaint)

        val facePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
        }
        canvas.drawCircle(playerX - 12f, playerY - 10f, 4f, facePaint)
        canvas.drawCircle(playerX + 12f, playerY - 10f, 4f, facePaint)
        canvas.drawLine(playerX - 14f, playerY + 10f, playerX + 14f, playerY + 10f, facePaint)
    }
}
