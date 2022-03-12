package cn.rubintry.rtmanager

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "runtime_custom_host")
data class CustomRuntimeInfo(@PrimaryKey(autoGenerate = true) val id : Long = 0, val host: String, val principal: String) : Serializable