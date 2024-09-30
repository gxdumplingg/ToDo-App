package com.example.todoapp.ui.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.CalendarView
import java.util.*

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CalendarView(context, attrs, defStyleAttr) {

    private val circlePaint = Paint().apply {
        color = Color.YELLOW // Màu sắc là màu vàng
        style = Paint.Style.FILL
    }
    private var selectedDate: Long = -1

    init {
        setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            selectedDate = calendar.timeInMillis
            invalidate() // Yêu cầu vẽ lại view
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (selectedDate != -1L) {
            // Tính toán vị trí của ô tròn cho ngày được chọn
            val cx = width / 2f // Tọa độ X
            val cy = height / 2f // Tọa độ Y

            // Vẽ hình tròn quanh ngày được chọn
            canvas.drawCircle(cx, cy, 50f, circlePaint) // Kích thước hình tròn có thể điều chỉnh
        }
    }
}
