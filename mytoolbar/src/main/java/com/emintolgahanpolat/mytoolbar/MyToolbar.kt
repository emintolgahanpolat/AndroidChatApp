package com.emintolgahanpolat.mytoolbar


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import android.widget.LinearLayout
import androidx.annotation.MenuRes
import androidx.core.view.setMargins


/**
 * Created by emintolgahanpolat on 2019-09-17
 */


class MyToolbar : RelativeLayout, ICustomToolbar {


    private lateinit var toolbar: RelativeLayout

    private lateinit var toolbarTitle: TextView
    private lateinit var toolbarProfileImage: ImageView
    private lateinit var btnContainer: LinearLayout


    constructor(context: Context) : this(context, null)

    constructor (context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor (context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {

        init(context)
        initAttrs(context, attrs)
    }

    private fun init(context: Context) {
        val rootView = View.inflate(context, R.layout.toolbar, this)
        toolbar = rootView.findViewById(R.id.customToolbar)

        toolbarTitle = rootView.findViewById(R.id.toolbarTitle) as TextView
        toolbarProfileImage = rootView.findViewById(R.id.toolbarProfileImage) as ImageView

        btnContainer = rootView.findViewById(R.id.btnContainer) as LinearLayout


    }


    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val attributes = context!!.obtainStyledAttributes(attrs, R.styleable.CustomActionBar, 0, 0)

        val title = attributes.getString(R.styleable.CustomActionBar_title)
        val profileImageUri = attributes.getResourceId(R.styleable.CustomActionBar_profileImage, -1)
        val menuResource = attributes.getResourceId(R.styleable.CustomActionBar_menuResource, -1)

        setTitle(title)
        if (profileImageUri == -1) {
            toolbarProfileImage.visibility = View.GONE
        } else {
            toolbarProfileImage.visibility = View.VISIBLE
            setProfileImage(profileImageUri, null)
        }

        if (menuResource >= 0) {
            setMenuResource(menuResource)
        }
        attributes.recycle()

    }

    private fun setMenuResource(@MenuRes menuRes: Int) {

    }


    override fun setTitle(title: String?) {
        if (title != null) {
            toolbarTitle.text = title
        }
    }

    override fun setTitle(title: Int) {
        toolbarTitle.text = context.getString(title)
    }


    override fun setProfileImage(url: String, @DrawableRes placeholder: Int, clickListener: OnClickListener?) {
        toolbarProfileImage.setOnClickListener(clickListener)
        AsyncTaskLoadImage(object : ImageDownloaderListener {
            override fun loaded(bm: Bitmap) {
                toolbarProfileImage.setImageBitmap(BitmapHelper.circualBitmap(bm))

            }

            override fun failed() {
                toolbarProfileImage.setImageResource(placeholder)
            }
        }).execute(url)
    }


    override fun setProfileImage(@DrawableRes image: Int, clickListener: OnClickListener?) {
        toolbarProfileImage.setOnClickListener(clickListener)
        toolbarProfileImage.setImageResource(image)
    }

    override fun addAction(image: Int, clickListener: OnClickListener) {
        val btn = ImageButton(context)
        val widthHeight = dpToPx(context, 40)
        val layoutParams = LinearLayout.LayoutParams(widthHeight, widthHeight)
        layoutParams.setMargins(dpToPx(context, 3))
        btn.layoutParams = layoutParams
        btn.adjustViewBounds=true
        btn.setImageResource(image)
        btn.setBackgroundResource(R.drawable.mask)
        btn.setOnClickListener(clickListener)
        btnContainer.addView(btn)
    }


}


interface ICustomToolbar {

    fun setTitle(title: String?)
    fun setTitle(@StringRes title: Int)
    fun setProfileImage(
        url: String, @DrawableRes placeholder: Int = R.drawable.empty_profile,
        clickListener: View.OnClickListener?
    )

    fun setProfileImage(@DrawableRes image: Int, clickListener: View.OnClickListener?)
    fun addAction(@DrawableRes image: Int, clickListener: View.OnClickListener)

}