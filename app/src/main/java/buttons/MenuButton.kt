package buttons

import android.content.Context
import android.util.AttributeSet

class MenuButton(context: Context, attributeSet: AttributeSet) : CustomButton(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val height = if (heightMode == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            (1.8f * fontPaint.textSize).toInt()
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height)
    }

}