package com.emintolgahanpolat.chat.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.URL


/**
 * Created by emintolgahanpolat on 2019-09-17
 */

class AsyncTaskLoadImage(val listener:ImageDownloaderListener) : AsyncTask<String, String, Bitmap>() {
    override fun doInBackground(vararg params: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(params[0])
            bitmap = BitmapFactory.decodeStream(url.getContent() as InputStream)
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }

        return bitmap
    }

    override fun onPostExecute(bitmap: Bitmap) {

        listener.loaded(bitmap)

    }

    companion object {
        private val TAG = "AsyncTaskLoadImage"
    }
}

interface ImageDownloaderListener{
    fun loaded(bm:Bitmap)
    fun failed()
}
