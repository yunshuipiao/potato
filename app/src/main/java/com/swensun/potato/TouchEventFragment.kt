package com.swensun.potato


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TouchEventFragment : Fragment(){


    companion object {
        @JvmStatic
        fun newInstance() =
            TouchEventFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_touch_event, container, false)
    }

}
