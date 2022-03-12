package cn.rubintry.rtmanager.db

import androidx.room.Database
import cn.rubintry.rtmanager.CustomRuntimeInfo
import androidx.room.RoomDatabase

@Database(entities = [CustomRuntimeInfo::class], version = 2, exportSchema = false)
abstract class RuntimeDatabase : RoomDatabase() {
    abstract fun customDao(): CustomDao
}