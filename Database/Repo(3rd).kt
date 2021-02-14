// package com.onepos.posandroidv2.database.repository

import android.annotation.SuppressLint
import android.content.Context
import com.onepos.posandroidv2.database.AppDatabase
import com.onepos.posandroidv2.database.entity.Category

class CategoryRepo(val context: Context) {

    private val db= AppDatabase.getInstance(context)
    private val categoryDao=db.categoryDao()
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: CategoryRepo? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CategoryRepo(context).also {
                    INSTANCE = it
                }
            }
    }

    fun insert(category: Category)=categoryDao.insert(category)

    fun insertall(vararg dataEntities: Category)=categoryDao.insertAll(*dataEntities)

    fun getall() = categoryDao.getAll()

    fun getfirst() = categoryDao.getFirst()

    fun delete(category: Category) = categoryDao.delete(category)
}