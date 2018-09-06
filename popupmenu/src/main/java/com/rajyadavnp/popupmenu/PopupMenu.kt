package com.rajyadavnp.popupmenu

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView


public class PopupMenu(private val context: Context) {
    private val hsScroll: HorizontalScrollView
    private val llMain: LinearLayout
    private var window: PopupWindow? = null
    private var textView: TextView? = null

    public enum class PopupAnimation(val value: Int) {
        TRANSLATE(1),
        SCALE(2)
    }

    init {
        this.window = PopupWindow(context)
        window!!.isOutsideTouchable = true
        window!!.isFocusable = true
        setAnimation(PopupAnimation.TRANSLATE)
        window!!.setBackgroundDrawable(null)
        this.hsScroll = HorizontalScrollView(context)
        this.llMain = LinearLayout(context)
        llMain.orientation = LinearLayout.HORIZONTAL
        hsScroll.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        llMain.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        llMain.setBackgroundResource(R.drawable.popup_bg)
    }

    fun addMenu(item: Option): PopupMenu {
        textView = TextView(context)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        textView!!.layoutParams = params
        textView!!.setPadding(30, 30, 30, 30)
        textView!!.text = item.title
        textView!!.isAllCaps = true
        textView!!.setTextColor(item.color)
        textView!!.setTypeface(textView!!.typeface, Typeface.BOLD)
        textView!!.isClickable = true
        textView!!.isFocusable = true
        textView!!.minWidth = 200
        textView!!.maxWidth = 400
        textView!!.maxLines = 1
        textView!!.ellipsize = TextUtils.TruncateAt.END
        textView!!.gravity = Gravity.CENTER
        textView!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f)
        val attrs = intArrayOf(android.R.attr.selectableItemBackground)
        val ta = context.obtainStyledAttributes(attrs)
        textView!!.background = ta.getDrawable(0)
        ta.recycle()
        textView!!.setCompoundDrawablesWithIntrinsicBounds(0, item.icon, 0, 0)
        textView!!.compoundDrawablePadding = 10
        textView!!.setOnClickListener {
            try {
                item.listener!!.onSelect()
                window!!.dismiss()
            } catch (e: Exception) {
                throw SelectException(e.message!!)
            }
        }
        llMain.addView(textView)
        hsScroll.addView(llMain)
        return this
    }

    public fun setAnimation(style: PopupAnimation): PopupMenu {
        if (style == PopupAnimation.TRANSLATE) {
            window!!.animationStyle = R.style.PopupWindowAnimationTranslate
        } else if (style == PopupAnimation.SCALE) {
            window!!.animationStyle = R.style.PopupWindowAnimation
        }
        return this
    }

    public fun showAt(view: View) {
        window!!.contentView = hsScroll
        val loc = IntArray(2)
        view.getLocationInWindow(loc)
        window!!.showAtLocation(view, Gravity.TOP or Gravity.RIGHT or Gravity.END, 0, loc[1])
    }

    public fun showAt(view: View, offsetX: Int, offsetY: Int) {
        window!!.contentView = hsScroll
        val loc = IntArray(2)
        view.getLocationInWindow(loc)
        window!!.showAtLocation(view, Gravity.TOP or Gravity.RIGHT or Gravity.END, offsetX, loc[1] + offsetY)
    }

    public class Option {
        var icon: Int = 0
        var title: String? = null
        var color: Int = Color.BLACK
        var listener: SelectListener? = null

        constructor() {}

        constructor(icon: Int, title: String) {
            this.icon = icon
            this.title = title
        }

        public fun setSelectListener(listener: SelectListener) {
            this.listener = listener
        }

        interface SelectListener {
            fun onSelect()
        }
    }

    public class SelectException() : Exception() {
        constructor(message: String) : this()
    }

}
