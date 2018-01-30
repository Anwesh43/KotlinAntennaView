package ui.anwesome.com.antennaview

/**
 * Created by anweshmishra on 31/01/18.
 */
import android.view.*
import android.graphics.*
import android.content.*
class AntennaView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}