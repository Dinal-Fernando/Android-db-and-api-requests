package com.onepos.posandroidv2.AppCommonClasses

import android.content.Context
import android.content.res.Resources
import com.android.volley.Response
import com.onepos.posandroidv2.MySingleton
import org.json.JSONObject

class AuthorizedRequestHandler(method: Int,
                               url: String?,
                               jsonRequest: JSONObject?,
                               listener: Response.Listener<JSONObject>,
                               private val context: Context,
                               resources: Resources?,
                               listner:OnErrorInteractionListner
                               ) {
    private val customErrorListener:AuthorizedJsonObjectRequest.CustomErrorListener =
        AuthorizedJsonObjectRequest.CustomErrorListener(context,resources,listner)
    private val request: AuthorizedJsonObjectRequest
    init {
        request = AuthorizedJsonObjectRequest(
            method,
            url,
            jsonRequest,
            listener,
            customErrorListener,
            context
        )
        customErrorListener.request=request
    }

    fun send(){
        MySingleton.getInstance(context).addToRequestQueue(request)
    }
}