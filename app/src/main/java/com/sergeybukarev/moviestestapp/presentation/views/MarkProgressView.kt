package com.sergeybukarev.moviestestapp.presentation.views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.PathShape
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import com.sergeybukarev.moviestestapp.R

class MarkProgressView : View {
    private val startAngleOffset = -90F

    // region Public API
    var maxProgress: Int = 100

    var color: Int
        @ColorInt get() = progressArcDrawable.paint.color
        set(@ColorInt value) {
            progressArcDrawable.paint.color = value
        }

    var progress = 0
        set(value) {
            val newValue = value.coerceIn(0, maxProgress)
            if (field == newValue) return
            field = newValue
            angle = convertProgressToAngle(newValue)
        }

    var strokeWidth = 0F
        set(value) {
            if (field == value) return
            field = value
            updateDrawInset()
            progressArcDrawable.paint.strokeWidth = value
            updateArcs()
        }

    // endregion

    // region Displaying Circle

    var angle = 0F
        private set(value) {
            val newValue = value.coerceIn(0F, 360F)
            if (field == newValue) return
            field = newValue
            updateArcs()
        }

    private val progressArcDrawable = ShapeDrawable(createProgressArcShape()).apply {
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.color = Color.GREEN
    }

    private fun updateArcs() {
        progressArcDrawable.shape = createProgressArcShape()
    }

    private fun createProgressArcShape(): PathShape {
        val arcPath = Path().apply {
            addArc(RectF(drawInset, drawInset, width.toFloat() - drawInset, height.toFloat() - drawInset), startAngleOffset, angle)
        }
        return PathShape(arcPath, width.toFloat(), height.toFloat())
    }

    // endregion

    // region Other

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateArcs()
    }

    private var drawInset = 0F

    private fun updateDrawInset() {
        drawInset = strokeWidth / 2F
    }

    private fun convertProgressToAngle(progress: Int): Float {
        return 360F * (progress.toFloat() / maxProgress.toFloat())
    }

    // endregion

    // region Initializing

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (isInEditMode) {
            progress = 30
        }
        val a = context.obtainStyledAttributes(attrs, R.styleable.MarkProgressView)
        try {
            maxProgress = a.getInt(R.styleable.MarkProgressView_maxProgress, maxProgress)
            strokeWidth = a.getDimension(R.styleable.MarkProgressView_strokeWidth, strokeWidth)
            color = a.getColor(R.styleable.MarkProgressView_android_color, color)
        } finally {
            a.recycle()
        }
    }

    init {
        val background = LayerDrawable(arrayOf(progressArcDrawable))
        ViewCompat.setBackground(this, background)
    }

    // endregion

}

