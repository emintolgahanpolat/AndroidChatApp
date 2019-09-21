package com.emintolgahanpolat.chat.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.emintolgahanpolat.chat.R


/**
 * Created by emintolgahanpolat on 2019-09-16
 */

class MessageInput : RelativeLayout, View.OnClickListener, TextWatcher, View.OnFocusChangeListener {


    private lateinit var inputEditText: EditText


    private var mContext: Context? = null
    private lateinit var button: ImageButton
    private lateinit var attachmentButton: ImageButton

    private var input: CharSequence? = null
    private var inputListener: InputListener? = null
    private var attachmentsListener: AttachmentsListener? = null
    private var isTyping: Boolean = false
    private var typingListener: TypingListener? = null
    private var delayTypingStatusMillis: Int = 0

    private val typingTimerRunnable = Runnable {
        if (isTyping) {
            isTyping = false
            if (typingListener != null) typingListener!!.onStopTyping()
        }
    }
    private var lastFocus: Boolean = false

    constructor(context: Context) : super(context) {

        this.mContext = context
        init()
    }

    constructor (context: Context, attrs: AttributeSet) : super(context, attrs) {

        this.mContext = context
        init()
        initAttrs(attrs)
    }



    constructor (context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {

        this.mContext = context
        init()
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet) {




    }

    /**
     * Sets callback for 'submit' button.
     *
     * @param inputListener input callback
     */
    fun setInputListener(inputListener: InputListener) {
        this.inputListener = inputListener
    }

    /**
     * Sets callback for 'add' button.
     *
     * @param attachmentsListener input callback
     */
    fun setAttachmentsListener(attachmentsListener: AttachmentsListener) {
        this.attachmentsListener = attachmentsListener
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.messageSendButton) {
            val isSubmitted = onSubmit()
            if (isSubmitted) {
                inputEditText.setText("")
            }
            removeCallbacks(typingTimerRunnable)
            post(typingTimerRunnable)
        } else if (id == R.id.attachmentButton) {
            onAddAttachments()
        }
    }

    /**
     * This method is called to notify you that, within s,
     * the count characters beginning at start have just replaced old text that had length before
     */
    override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        input = s
        button.isEnabled = input!!.length > 0
        if (s.length > 0) {
            if (!isTyping) {
                isTyping = true
                if (typingListener != null) typingListener!!.onStartTyping()
            }
            removeCallbacks(typingTimerRunnable)
            postDelayed(typingTimerRunnable, delayTypingStatusMillis.toLong())
        }
    }

    /**
     * This method is called to notify you that, within s,
     * the count characters beginning at start are about to be replaced by new text with length after.
     */
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        //do nothing
    }

    /**
     * This method is called to notify you that, somewhere within s, the text has been changed.
     */
    override fun afterTextChanged(editable: Editable) {
        //do nothing
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (lastFocus && !hasFocus && typingListener != null) {
            typingListener!!.onStopTyping()
        }
        lastFocus = hasFocus
    }

    private fun onSubmit(): Boolean {
        return inputListener != null && inputListener!!.onSubmit(input)
    }

    private fun onAddAttachments() {
        if (attachmentsListener != null) attachmentsListener!!.onAddAttachments()
    }



    private fun init() {
        val rootView = View.inflate(mContext, R.layout.custom_chat_input, this)


        inputEditText = rootView.findViewById<View>(R.id.messageInput) as EditText
        button = rootView.findViewById<View>(R.id.messageSendButton) as ImageButton
        attachmentButton = rootView.findViewById<View>(R.id.attachmentButton) as ImageButton

        button.setOnClickListener(this)
        attachmentButton.setOnClickListener(this)
        inputEditText.addTextChangedListener(this)
        inputEditText.setText("")
        inputEditText.onFocusChangeListener = this
    }

    private fun setCursor(drawable: Drawable?) {
        if (drawable == null) return

        try {
            val drawableResField = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            drawableResField.isAccessible = true

            val drawableFieldOwner: Any
            val drawableFieldClass: Class<*>
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                drawableFieldOwner = this.inputEditText
                drawableFieldClass = TextView::class.java
            } else {
                val editorField = TextView::class.java.getDeclaredField("mEditor")
                editorField.isAccessible = true
                drawableFieldOwner = editorField.get(this.inputEditText)
                drawableFieldClass = drawableFieldOwner.javaClass
            }
            val drawableField = drawableFieldClass.getDeclaredField("mCursorDrawable")
            drawableField.isAccessible = true
            drawableField.set(drawableFieldOwner, arrayOf(drawable, drawable))
        } catch (ignored: Exception) {
        }

    }

    fun setTypingListener(typingListener: TypingListener) {
        this.typingListener = typingListener
    }

    /**
     * Interface definition for a callback to be invoked when user pressed 'submit' button
     */
    interface InputListener {

        /**
         * Fires when user presses 'send' button.
         *
         * @param input input entered by user
         * @return if input text is valid, you must return `true` and input will be cleared, otherwise return false.
         */
        fun onSubmit(input: CharSequence?): Boolean
    }

    /**
     * Interface definition for a callback to be invoked when user presses 'add' button
     */
    interface AttachmentsListener {

        /**
         * Fires when user presses 'add' button.
         */
        fun onAddAttachments()
    }

    /**
     * Interface definition for a callback to be invoked when user typing
     */
    interface TypingListener {

        /**
         * Fires when user presses start typing
         */
        fun onStartTyping()

        /**
         * Fires when user presses stop typing
         */
        fun onStopTyping()

    }
}
