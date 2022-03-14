package cn.rubintry.rtmanager.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "runtime_icon_position")
data class IconPos(@PrimaryKey(autoGenerate = true) var id: Long = 0,  var left: Int, val top: Int)
