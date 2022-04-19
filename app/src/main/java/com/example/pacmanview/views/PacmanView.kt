package com.example.pacmanview.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.pacmanview.R

class PacmanView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {

    companion object {
        private const val START_ANGEL = 25f
        private const val MAX_ANGEL = 315f
        private const val MARGIN_COEFFICIENT = 0.8f
        private const val RADIUS_EYE_COEFFICIENT = 0.07f
        private const val Y_EYE_COEFFICIENT = 0.25f
        private const val X_EYE_COEFFICIENT = 0.55f
        private const val TIME_ANIMATOR = 1000L
        private const val ZERO = 0f
    }

    private val pacmanPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val eyePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var pacmanColor: Int = 0
        set(value) {
            pacmanPaint.color = value
            field = value
        }

    private var eyeColor: Int = 0
        set(value) {
            eyePaint.color = value
            field = value
        }

    private val pacmanContourPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.PacmanView, 0, 0
        ).apply {
            pacmanColor =
                getColor(R.styleable.PacmanView_colorPacman, context.getColor(R.color.yellow))
            eyeColor =
                getColor(R.styleable.PacmanView_colorEye, context.getColor(R.color.black))
        }
    }

    private var radiusEye = 0f
    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f
    private val oval: RectF by lazy {
        RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
    }

    private val eatAnimation: ValueAnimator
        get() = ValueAnimator.ofFloat(sweepAngel, sweepAngel + 23f)
            .apply {
                duration = TIME_ANIMATOR
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    sweepAngel = it.animatedValue as Float
                    invalidate()
                }
            }

    private var sweepAngel = ZERO
    private val negativeSweepAngel get() = -sweepAngel

    fun startAnimation() {
        eatAnimation.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        radiusEye = width * RADIUS_EYE_COEFFICIENT
        radius = width / 2f * MARGIN_COEFFICIENT
        centerX = width / 2f
        centerY = height / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(
            oval,
            START_ANGEL + negativeSweepAngel,
            MAX_ANGEL + sweepAngel + sweepAngel,
            true,
            pacmanPaint
        )

        canvas.drawArc(
            oval,
            START_ANGEL + negativeSweepAngel,
            MAX_ANGEL + sweepAngel + sweepAngel,
            true,
            pacmanContourPaint
        )

        canvas.drawCircle(
            width * X_EYE_COEFFICIENT,
            height * Y_EYE_COEFFICIENT,
            radiusEye,
            eyePaint
        )
    }
}