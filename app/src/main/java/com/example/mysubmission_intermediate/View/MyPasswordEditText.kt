package com.example.mysubmission_intermediate.View

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.mysubmission_intermediate.R

class MyPasswordEditText : AppCompatEditText {

    private lateinit var passwordIconDrawable: Drawable
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        transformationMethod = PasswordTransformationMethod.getInstance()
    }

    @SuppressLint("NewApi")
    private fun init() {
        passwordIconDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_24) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        compoundDrawablePadding = 20

        setHint("password")
        setAutofillHints(AUTOFILL_HINT_PASSWORD)
        setDrawable(passwordIconDrawable)

        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && s.length < 6)
                    error = context.getString(R.string.message_password_error)
            }
        })
    }

    private fun setDrawable(startOfTheText: Drawable? = null, top0fTheText: Drawable? = null, end0fTheText: Drawable? = null, bottom0fTheText: Drawable? = null ) {
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, top0fTheText, end0fTheText, bottom0fTheText)
    }
}