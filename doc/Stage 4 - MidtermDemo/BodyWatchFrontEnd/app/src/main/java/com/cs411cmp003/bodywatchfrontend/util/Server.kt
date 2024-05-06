package com.cs411cmp003.bodywatchfrontend.util

import android.util.Log
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

/**
 * open server, set port be a fixed number
 */
class Server {
    private var socket: Socket? = null
    var serverSocket: ServerSocket? = null

    fun startServer(port: Int) {
        Thread {
            try {
                serverSocket = ServerSocket(port)
                Log.e(TAG, "wait connecting--->")

                Log.e(
                   TAG,
                    "Connected, ip address：" + socket?.inetAddress + ", local host：" + socket?.localAddress
                )
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }.start()
    }

    companion object {
        private const val TAG = "Sever"
    }
}