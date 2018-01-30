package ui.anwesome.com.antennaview

/**
 * Created by anweshmishra on 31/01/18.
 */
import android.app.Activity
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
    data class Animator(var view:View,var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class AntennaState(var scale:Float = 0f,var dir:Float = 0f,var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(dir == 0f) {
                dir = 1 - 2 * scale
                startcb()
            }
        }
    }
    data class Antenna(var w:Float,var h:Float) {
        val state = AntennaState()
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.save()
            canvas.translate(w/2,h/2)
            canvas.rotate(-90f*state.scale+45)
            paint.style = Paint.Style.STROKE
            canvas.drawCircle(0f,-h/20,h/20,paint)
            val path = Path()
            for(i in 60..120) {
                val x = (w/3)*Math.cos(i*Math.PI/180).toFloat()
                val y = -(h/20+w/3) + (w/3)*Math.sin(i*Math.PI/180).toFloat()
                if(i == 60) {
                    path.moveTo(x,y)
                }
                else {
                    path.lineTo(x,y)
                }
            }
            val fx = (w/3)*Math.cos(Math.PI/3).toFloat()
            val fy = -(h/20+w/3) + (w/3)*Math.sin(Math.PI/3).toFloat()
            path.lineTo(fx,fy)
            canvas.drawPath(path,paint)
            canvas.drawLine(0f,fy,0f,fy-w/4,paint)
            canvas.drawCircle(0f,fy-w/4-h/20,h/20,paint)
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
        }
    }
    data class AntennaRenderer(var view:AntennaView,var time:Int = 0) {
        var antenna:Antenna?=null
        val animator = Animator(view)
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                antenna = Antenna(w,h)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            antenna?.draw(canvas,paint)
            time++
            animator.animate {
                antenna?.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            antenna?.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity:Activity):AntennaView {
            val view = AntennaView(activity)
            activity.setContentView(view)
            return view
        }
    }
}