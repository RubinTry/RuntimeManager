package cn.rubintry.rtmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CustomRuntimeInfo::class , IconPos::class], version = 2, exportSchema = false)
abstract class RuntimeDatabase : RoomDatabase() {
    abstract fun customDao(): CustomDao

    abstract fun iconPositionDao() : IconPositionDao
}