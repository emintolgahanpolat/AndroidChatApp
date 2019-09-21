package com.emintolgahanpolat.chat.ui


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emintolgahanpolat.chat.R
import com.emintolgahanpolat.mytoolbar.AsyncTaskLoadImage
import com.emintolgahanpolat.mytoolbar.BitmapHelper
import com.emintolgahanpolat.mytoolbar.ImageDownloaderListener
import kotlinx.android.synthetic.main.fragment_chat_list.*


class ChatListFragment : Fragment() {
    private var mView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_chat_list, container, false)
        return mView
    }




}
