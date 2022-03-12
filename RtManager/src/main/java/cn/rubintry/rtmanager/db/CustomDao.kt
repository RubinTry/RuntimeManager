package cn.rubintry.rtmanager.db

import androidx.room.*
import cn.rubintry.rtmanager.CustomRuntimeInfo


@Dao
interface CustomDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg customDao: CustomRuntimeInfo)

    @Query("SELECT * FROM runtime_custom_host")
    fun getAll() : List<CustomRuntimeInfo>

    @Query("SELECT * FROM runtime_custom_host WHERE principal = :principal")
    fun getAllByPrincipal(principal: String) : List<CustomRuntimeInfo>

    @Update(entity = CustomRuntimeInfo::class , onConflict = OnConflictStrategy.REPLACE)
    fun updateById(info: CustomRuntimeInfo)
}