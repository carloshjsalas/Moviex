package com.moviex.fragments

import android.os.Bundle
import com.moviex.MainActivity
import com.moviex.protocol.CommunicationCallback
import dagger.android.support.DaggerFragment

abstract class BaseMoviesFragment : DaggerFragment() {

    lateinit var communication: CommunicationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communication = (requireActivity() as MainActivity)
    }

}