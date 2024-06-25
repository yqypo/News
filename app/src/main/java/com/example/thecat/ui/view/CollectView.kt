package com.example.thecat.ui.view

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import com.example.thecat.R
import com.example.thecat.ui.activity.LoginActivity
import com.example.thecat.utils.UserUtils.data
import per.goweii.heartview.HeartView
import per.goweii.reveallayout.RevealLayout
import java.lang.Boolean
import kotlin.Int

class CollectView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RevealLayout(context, attrs, defStyleAttr) {
    private var mOnClickListener: OnClickListener? = null
    private var mUncheckedColor = 0

    override fun initAttr(attrs: AttributeSet) {
        super.initAttr(attrs)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CollectView)
        mUncheckedColor = typedArray.getColor(R.styleable.CollectView_cv_uncheckedColor, 0)
        typedArray.recycle()
        setCheckWithExpand(true)
        setUncheckWithExpand(false)
        setAnimDuration(500)
        setAllowRevert(true)
    }

    override fun getCheckedLayoutId(): Int {
        return R.layout.layout_collect_view_checked
    }

    override fun getUncheckedLayoutId(): Int {
        return R.layout.layout_collect_view_unchecked
    }

    override fun createUncheckedView(): View {
        val view = super.createUncheckedView()
        if (view is HeartView) {
            val heartView = view
            if (mUncheckedColor != 0) {
                heartView.setColor(mUncheckedColor)
                heartView.setEdgeColor(mUncheckedColor)
            }
        }
        return view
    }

    fun setOnClickListener(onClickListener: OnClickListener?) {
        mOnClickListener = onClickListener
        setOnClickListener(OnClickListener {
            if (Boolean.FALSE == data.value) {
                isChecked = false
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                return@OnClickListener
            }
            if (mOnClickListener != null) {
                mOnClickListener?.onClick(this@CollectView);
            }
        })
    }

    interface OnClickListener {
        fun onClick(v: CollectView?)
    }
}