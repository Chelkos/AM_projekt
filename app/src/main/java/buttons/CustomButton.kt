package buttons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.amprojekt.R
import java.lang.Integer.min

open class CustomButton(context : Context, attributeSet : AttributeSet) : View(context, attributeSet) {

    protected var backgroundPaint = Paint().apply {
        color = resources.getColor(R.color.imperial_blue, null)
    }
    protected var fontPaint = Paint().apply {
        color = resources.getColor(R.color.gold, null)
        textSize = 70f
        textAlign = Paint.Align.CENTER
    }
    protected var text : String = context.obtainStyledAttributes(attributeSet, intArrayOf(android.R.attr.text)).getText(0).toString()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas == null) return

        val radius = 0.33f * min(width, height)
        canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), radius, radius, backgroundPaint)
        canvas.drawText(text, width / 2f, height / 2f + fontPaint.textSize / 4, fontPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN) {
            alpha = 0.85f
        }
        if(event?.action == MotionEvent.ACTION_UP) {
            alpha = 1.0f
            val x = event.x
            val y = event.y
            if(x >= 0f && x <= width && y >= 0f && y <= height) {
                performClick()
            }
        }
        invalidate()
        return true
    }

    fun changeTextSize(size : Float) {
        fontPaint.textSize = size
        invalidate()
    }

    fun getContent() : String = text

}