package id.sansets.libraries.expandable_textview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class ExpandableTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        const val ANIMATION_PROPERTY_MAX_HEIGHT = "maxHeight"
        const val ANIMATION_PROPERTY_ALPHA = "alpha"
        const val DEFAULT_ELLIPSIZED_TEXT = "..."
        const val MAX_VALUE_ALPHA = 255
        const val MIN_VALUE_ALPHA = 0
    }

    private var visibleLines: Int = 0
    private var isExpanded: Boolean = false
    private var animationDuration: Int = 0
    private var foregroundColor: Int = 0
    private var ellipsizeText: String = ""
    private var initialValue: String = ""
    private var isUnderlined: Boolean = false
    private var ellipsizeTextColor: Int = 0

    private val visibleText by lazy { visibleText() }

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        visibleLines = attributes.getInt(R.styleable.ExpandableTextView_visibleLines, 3)
        isExpanded = attributes.getBoolean(R.styleable.ExpandableTextView_isExpanded, false)
        animationDuration = attributes.getInt(R.styleable.ExpandableTextView_duration, 250)
        foregroundColor =
            attributes.getColor(R.styleable.ExpandableTextView_foregroundColor, Color.TRANSPARENT)
        ellipsizeText = attributes.getString(R.styleable.ExpandableTextView_ellipsizeText)
            ?: context.getString(R.string.view_more)
        isUnderlined = attributes.getBoolean(R.styleable.ExpandableTextView_isUnderlined, false)
        ellipsizeTextColor =
            attributes.getColor(R.styleable.ExpandableTextView_ellipsizeTextColor, Color.BLUE)
        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (initialValue.isBlank()) {
            initialValue = text.toString()

            setMaxLines(isExpanded)
            setForeground(isExpanded)
            setEllipsizedText(isExpanded)
        }
    }

    fun toggle() {
        if (visibleText.isAllTextVisible()) {
            return
        }

        isExpanded = !isExpanded

        if (isExpanded) {
            setEllipsizedText(isExpanded)
        }

        val startHeight = measuredHeight
        setMaxLines(isExpanded)
        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        val endHeight = measuredHeight

        animationSet(startHeight, endHeight).apply {
            duration = animationDuration.toLong()
            start()

            addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    if (!isExpanded) {
                        setEllipsizedText(isExpanded)
                    }
                }

                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
        }
    }

    fun setVisibleLines(visibleLines: Int): ExpandableTextView {
        this.visibleLines = visibleLines
        return this
    }

    fun setIsExpanded(isExpanded: Boolean): ExpandableTextView {
        this.isExpanded = isExpanded
        return this
    }

    fun setAnimationDuration(animationDuration: Int): ExpandableTextView {
        this.animationDuration = animationDuration
        return this
    }

    fun setIsUnderlined(isUnderlined: Boolean): ExpandableTextView {
        this.isUnderlined = isUnderlined
        return this
    }

    fun setEllipsizedText(ellipsizeText: String): ExpandableTextView {
        this.ellipsizeText = ellipsizeText
        return this
    }

    fun setEllipsizedTextColor(ellipsizeTextColor: Int): ExpandableTextView {
        this.ellipsizeTextColor = ellipsizeTextColor
        return this
    }

    fun setForegroundColor(foregroundColor: Int): ExpandableTextView {
        this.foregroundColor = foregroundColor
        return this
    }

    private fun setEllipsizedText(isExpanded: Boolean) {
        if (initialValue.isBlank()) {
            return
        }

        text = if (
            isExpanded ||
            visibleText.isAllTextVisible() ||
            visibleText.length < (ellipsizeText.length + DEFAULT_ELLIPSIZED_TEXT.length)
        ) {
            initialValue
        } else {
            SpannableStringBuilder(
                visibleText.substring(
                    0,
                    visibleText.length - (ellipsizeText.length + DEFAULT_ELLIPSIZED_TEXT.length)
                )
            )
                .append(DEFAULT_ELLIPSIZED_TEXT)
                .append(ellipsizeText.span())

        }
    }

    private fun visibleText(): String {
        var end = 0

        for (i in 0 until visibleLines) {
            if (layout.getLineEnd(i) != 0)
                end = layout.getLineEnd(i)
        }

        return initialValue.substring(0, end)
    }

    private fun setMaxLines(isExpanded: Boolean) {
        maxLines = if (!isExpanded) {
            visibleLines
        } else {
            Integer.MAX_VALUE
        }
    }

    private fun setForeground(isExpanded: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            foreground = GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP,
                intArrayOf(foregroundColor, Color.TRANSPARENT)
            )

            foreground.alpha = if (isExpanded) {
                MIN_VALUE_ALPHA
            } else {
                MAX_VALUE_ALPHA
            }
        }
    }

    private fun animationSet(startHeight: Int, endHeight: Int): AnimatorSet {
        return AnimatorSet().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                playTogether(
                    ObjectAnimator.ofInt(
                        this@ExpandableTextView,
                        ANIMATION_PROPERTY_MAX_HEIGHT,
                        startHeight,
                        endHeight
                    ),
                    ObjectAnimator.ofInt(
                        this@ExpandableTextView.foreground,
                        ANIMATION_PROPERTY_ALPHA,
                        foreground.alpha,
                        MAX_VALUE_ALPHA - foreground.alpha
                    )
                )
            }
        }
    }

    private fun String.isAllTextVisible(): Boolean = this == text

    private fun String.span(): SpannableString =
        SpannableString(this).apply {
            setSpan(
                ForegroundColorSpan(ellipsizeTextColor),
                0,
                this.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            if (isUnderlined) {
                setSpan(UnderlineSpan(), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
}