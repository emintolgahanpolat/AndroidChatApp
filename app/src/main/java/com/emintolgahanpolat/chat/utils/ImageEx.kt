package com.emintolgahanpolat.chat.utils


import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso

/**
 * Created by emintolgahanpolat on 2019-09-17
 */


fun ImageView.loadImage(uri: Uri,@DrawableRes placeholder: Int) {
    Picasso.get().load(uri).placeholder(placeholder).error(placeholder).into(this)
}

