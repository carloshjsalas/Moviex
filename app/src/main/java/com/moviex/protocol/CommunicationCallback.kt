package com.moviex.protocol

interface CommunicationCallback {

    fun onFragmentEvent(action: ProtocolAction)
}