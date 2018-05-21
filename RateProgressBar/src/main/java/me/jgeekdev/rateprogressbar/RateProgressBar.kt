package me.jgeekdev.rateprogressbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class RateProgressBar : View {
    //进度背景色
    private var progressBgColor = 0xFFFFFFFF.toInt()
    //边框颜色
    private var strokeColor = 0xFF2CBAFF.toInt()
    //边框宽度
    private var strokeWidth = dp2px(1f)
    //进度颜色
    private var progressColor = 0xFF2CBAFF.toInt()
    //进度开始颜色
    private var progressStartColor = 0xFFFFBC00.toInt()
    //进度结束颜色
    private var progressEndColor = 0xFFFF9800.toInt()
    //进度颜色是否渐变
    private var progressGradient = false
    //进度是否在边框内
    private var progressInside = false
    //文字颜色
    private var textColor = 0xFF2CBAFF.toInt()
    //覆盖到进度的文字颜色
    private var textCoverColor = 0xFFFFFFFF.toInt()
    //文字大小
    private var textSize = sp2px(14f)
    //初始文字
    private var initText = "下载"
    //完成文字
    private var completeText = "下载完成"
    //进度最大值
    private var max = 100
    //当前进度
    private var progress = 0
    //为true时进度为0依然显示进度
    private var showRate = false

    private lateinit var paint: Paint
    private lateinit var bgRectF: RectF
    private var bgRadius = 0f
    private lateinit var strokeRectF: RectF
    private var strokeRadius = 0f
    private lateinit var progressRectF: RectF
    private var progressRadius = 0f
    private lateinit var clipRectF: RectF
    private lateinit var progressShader: Shader

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RateProgressBar)
        progressBgColor = ta.getColor(R.styleable.RateProgressBar_rpbProgressBgColor, progressBgColor)
        strokeColor = ta.getColor(R.styleable.RateProgressBar_rpbStrokeColor, strokeColor)
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RateProgressBar_rpbStrokeWidth, strokeWidth)
        progressColor = ta.getColor(R.styleable.RateProgressBar_rpbProgressColor, progressColor)
        progressStartColor = ta.getColor(R.styleable.RateProgressBar_rpbProgressStartColor, progressStartColor)
        progressEndColor = ta.getColor(R.styleable.RateProgressBar_rpbProgressEndColor, progressEndColor)
        progressGradient = ta.getBoolean(R.styleable.RateProgressBar_rpbProgressGradient, progressGradient)
        progressInside = ta.getBoolean(R.styleable.RateProgressBar_rpbProgressInside, progressInside)
        textColor = ta.getColor(R.styleable.RateProgressBar_rpbTextColor, textColor)
        textCoverColor = ta.getColor(R.styleable.RateProgressBar_rpbTextCoverColor, textCoverColor)
        textSize = ta.getDimensionPixelSize(R.styleable.RateProgressBar_rpbTextSize, textSize)
        initText = ta.getString(R.styleable.RateProgressBar_rpbInitText)
        completeText = ta.getString(R.styleable.RateProgressBar_rpbCompleteText)
        max = ta.getInt(R.styleable.RateProgressBar_rpbMax, max)
        progress = ta.getInt(R.styleable.RateProgressBar_rpbProgress, progress)
        showRate = ta.getBoolean(R.styleable.RateProgressBar_rpbShowRate, showRate)
        ta.recycle()

        paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dw = dp2px(160f)
        val dh = dp2px(20f)
        val measuredWidth = resolveSizeAndState(dw, widthMeasureSpec, 0)
        val measuredHeight = resolveSizeAndState(dh, heightMeasureSpec, 0)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bgRectF = RectF(0f, 0f, w.toFloat(), h.toFloat())
        bgRadius = h / 2f
        strokeRectF = RectF(strokeWidth / 2f, strokeWidth / 2f, w - strokeWidth / 2f, h - strokeWidth / 2f)
        strokeRadius = (h - strokeWidth) / 2f
        if (progressInside) {
            progressRectF = RectF(strokeWidth.toFloat(), strokeWidth.toFloat(), w - strokeWidth.toFloat(), h - strokeWidth.toFloat())
            progressRadius = h / 2f - strokeWidth
        } else {
            progressRectF = RectF(0f, 0f, w.toFloat(), h.toFloat())
            progressRadius = h / 2f
        }
        clipRectF = RectF(progressRectF)
        progressShader = LinearGradient(progressRectF.left, progressRectF.top, progressRectF.right, progressRectF.bottom,
                progressStartColor, progressEndColor, Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBg(canvas)
        drawStroke(canvas)
        drawProgress(canvas)
        drawText(canvas)
    }

    private fun drawBg(canvas: Canvas?) {
        canvas?.save()
        paint.color = progressBgColor
        paint.style = Paint.Style.FILL
        canvas?.drawRoundRect(bgRectF, bgRadius, bgRadius, paint)
        canvas?.restore()
    }

    private fun drawStroke(canvas: Canvas?) {
        canvas?.save()
        paint.color = strokeColor
        paint.strokeWidth = strokeWidth.toFloat()
        paint.style = Paint.Style.STROKE
        canvas?.drawRoundRect(strokeRectF, strokeRadius, strokeRadius, paint)
        canvas?.restore()
    }

    private fun drawProgress(canvas: Canvas?) {
        canvas?.save()
        clipRectF.right = progressRectF.right * progress / max
        canvas?.clipRect(clipRectF)
        if (progressGradient) {
            paint.shader = progressShader
        } else {
            paint.color = progressColor
        }
        paint.style = Paint.Style.FILL
        canvas?.drawRoundRect(progressRectF, progressRadius, progressRadius, paint)
        canvas?.restore()
    }

    private fun drawText(canvas: Canvas?) {
        val saved = canvas?.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)
        paint.color = textColor
        paint.textSize = textSize.toFloat()
        paint.textAlign = Paint.Align.CENTER
        val fontMetrics = paint.fontMetrics
        val baseLineY = height / 2 + ((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent)
        if (progress > 0) {
            if (progress == max) {
                canvas?.drawText(completeText, width / 2f, baseLineY, paint)
            } else {
                val rateText = (progress * 100 / max).toString() + "%"
                canvas?.drawText(rateText, width / 2f, baseLineY, paint)
            }
        } else {
            if (showRate) {
                canvas?.drawText("0%", width / 2f, baseLineY, paint)
            } else {
                canvas?.drawText(initText, width / 2f, baseLineY, paint)
            }
        }
        paint.color = textCoverColor
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas?.drawRect(clipRectF, paint)
        paint.xfermode = null
        saved?.let { canvas.restoreToCount(it) }
    }

    private fun dp2px(dpValue: Float): Int {
        val density = resources.displayMetrics.density
        return (dpValue * density + 0.5f).toInt()
    }

    private fun sp2px(spValue: Float): Int {
        val scaledDensity = resources.displayMetrics.scaledDensity
        return (spValue * scaledDensity + 0.5f).toInt()
    }

    fun setMax(max: Int) {
        this.max = max
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    fun showRate(showRate: Boolean) {
        this.showRate = showRate
        invalidate()
    }
}