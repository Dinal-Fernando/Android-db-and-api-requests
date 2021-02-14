package com.onepos.posandroidv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.onepos.posandroidv2.database.dao.*
import com.onepos.posandroidv2.database.entity.*
import java.util.concurrent.Executors

@Database(entities = [ Category::class, SystemDetail::class,Item::class, Order::class,OrderDetail::class],version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase :RoomDatabase(){
    abstract fun categoryDao(): CategoryDao
    abstract fun systemDetailDao(): SystemDetailDao
    abstract fun itemDao(): ItemDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) :AppDatabase{
            if (INSTANCE==null){
                INSTANCE ?: synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app-database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .addCallback(object: Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadExecutor().execute {
                                    getInstance(context).systemDetailDao().insertAll(*SystemDetail.populate())
                                }
                            }
                        })
                        .build()

                }
            }
            return INSTANCE!!
        }
    }
}