package com.onepos.posandroidv2

import android.content.Context

class MyPreference(context: Context){
    val PREFERENCE_NAME="LoginDetails"
    val PREFERENCE_Refresh_Token="Refresh_Token"
    val PREFERENCE_Access_Token="Access_Token"
    val Login_Status="Login_Status"

    val preference=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun getAccessToken():String{
        return preference.getString(PREFERENCE_Access_Token,"No").toString()
    }

    fun setAccessToken(token:String){
        val editor=preference.edit()
        editor.putString(PREFERENCE_Access_Token,token)
        editor.apply()
    }

    fun getRefreshToken():String{
        return preference.getString(PREFERENCE_Refresh_Token,"No").toString()
    }

    fun setRefreshToken(token:String){
        val editor=preference.edit()
        editor.putString(PREFERENCE_Refresh_Token,token)
        editor.apply()
    }

    fun getLoginStatus():String{
        return preference.getString(Login_Status,"false").toString()
    }

    fun setLoginStatus(status:String){
        val editor=preference.edit()
        editor.putString(Login_Status,status)
        editor.apply()
    }


}