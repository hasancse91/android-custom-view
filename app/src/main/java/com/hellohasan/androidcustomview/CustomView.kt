package com.hellohasan.androidcustomview

import androidx.constraintlayout.widget.ConstraintLayout

import android.content.Context
import android.widget.TextView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.Nullable
import kotlinx.android.synthetic.main.custom_view.view.*


class CustomView(context: Context, @Nullable attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var view : View = LayoutInflater.from(context).inflate(R.layout.custom_view, this, true)

    init {

        val title: String?
        val subtitle: String?
        val a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0)

        try {
            title = a.getString(R.styleable.CustomView_customViewTitle)
            subtitle = a.getString(R.styleable.CustomView_customViewSubtitle)
        } finally {
            a.recycle()
        }

        // Throw an exception if required attributes are not set
        if (title == null) {
            throw RuntimeException("No title provided")
        }
        if (subtitle == null) {
            throw RuntimeException("No subtitle provided")
        }

        initView(title, subtitle)
    }

    // Setup views
    private fun initView(title: String, subtitle: String) {
        val imageView = view.imageView as ImageView
        val titleView = view.titleTextView as TextView
        val subtitleView = view.subtitleTextView as TextView

        titleView.text = title
        subtitleView.text = subtitle
    }
}