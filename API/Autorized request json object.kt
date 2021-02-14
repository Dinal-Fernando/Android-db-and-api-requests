package com.onepos.posandroidv2.AppCommonClasses

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.onepos.posandroidv2.*
import org.json.JSONObject

class AuthorizedJsonObjectRequest(
    method: Int,
    url: String?,
    jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener,
    val context: Context
) : JsonObjectRequest(method, url, jsonRequest, listener, errorListener) {
    constructor(method: Int,
                url: String?,
                jsonRequest: JSONObject?,
                listener: Response.Listener<JSONObject>,
                context: Context,
                resources: Resources,
                listner:OnErrorInteractionListner
                ) : this(method,url,jsonRequest,listener,CustomErrorListener(context,resources,listner),context)
    init {
        retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
    }
    
    override fun getHeaders(): MutableMap<String, String> {

            val headers = HashMap<String, String>()
            headers["Content-Type"] = "application/json"
            val token=MyPreference(context).getAccessToken()
            val auth = "Bearer $token"
            headers["Authorization"] = auth
            return headers

    }

    class CustomErrorListener(var context: Context,var resources: Resources?,val listner:OnErrorInteractionListner):Response.ErrorListener{
        var isSecondTime=false
        lateinit var request:AuthorizedJsonObjectRequest
        override fun onErrorResponse(error: VolleyError?) {
            when(val statusCode = error?.networkResponse?.statusCode?:0){
                401->{
                        val url= DbContract.SERVER_URL+"/refresh/"
                        val token=MyPreference(context).getRefreshToken()
                        val jsonObject = JSONObject()
                        jsonObject.put("refresh",token)
                        val tokenRequest =
                            JsonObjectRequest(Method.POST, url, jsonObject, Response.Listener {response ->
                                if (response.has("access")){
                                    val accessToken = response.getString("access")
                                    MyPreference(context).setAccessToken(accessToken)
                                    MySingleton.getInstance(context).addToRequestQueue(request)
                                    isSecondTime=true
                                }
                            }, Response.ErrorListener {
                                ShowConnectingProgressDialog.mAlertDialogConnect.dismiss()
                                resources?.let { ShowCantConnectAlertDialog.show(context, it) }
                                listner.onErrorInteraction("401")

                                MyPreference(context).setLoginStatus("false")
                                context.startActivity(Intent(context,LoginActivity::class.java))

                            })
                        MySingleton.getInstance(context).addToRequestQueue(tokenRequest)
                    
                }
                else->{
                    ShowConnectingProgressDialog.mAlertDialogConnect.dismiss()
                    resources?.let { ShowCantConnectAlertDialog.show(context, it) }
                    listner.onErrorInteraction(statusCode.toString())
                }

            }
        }
    }
}