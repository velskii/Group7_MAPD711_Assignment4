package com.example.group7_mapd711_assignment4.User

import android.content.Context
import androidx.room.*

@Database(entities = arrayOf(UserModel::class), version = 1, exportSchema = false)

public abstract class UserDatabase: RoomDatabase() {

    abstract fun UserDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabaseClient(context: Context): UserDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, UserDatabase::class.java, "USERDB")
                    .fallbackToDestructiveMigration()
                    .build()
                return INSTANCE!!
            }
        }
    }
}