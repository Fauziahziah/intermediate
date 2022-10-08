package com.example.mysubmission_intermediate.View

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.mysubmission_intermediate.R

class MyEmailEdittext : AppCompatEditText {

    private lateinit var emailIconDrawable: Drawable
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @SuppressLint("NewApi")
    private fun init() {
        emailIconDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        compoundDrawablePadding = 20



        setHint("Email")
        setDrawable(emailIconDrawable)

        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(s).matches())
                    error = context.getString(R.string.message_email_error)
            }
        })

    }

    private fun setDrawable(startOfTheText: Drawable? = null, top0fTheText: Drawable? = null, end0fTheText: Drawable? = null, bottom0fTheText: Drawable? = null ) {
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, top0fTheText, end0fTheText, bottom0fTheText)
    }

}