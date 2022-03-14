package cn.rubintry.rtmanager.db

import androidx.room.*


@Dao
interface CustomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg customDao: CustomRuntimeInfo)

    @Query("SELECT * FROM runtime_custom_host")
    fun getAll() : List<CustomRuntimeInfo>

    @Query("SELECT * FROM runtime_custom_host WHERE principal = :principal")
    fun getAllByPrincipal(principal: String) : List<CustomRuntimeInfo>

    @Query("SELECT * FROM runtime_custom_host WHERE host = :runtime")
    fun getAllByRuntime(runtime: String) : List<CustomRuntimeInfo>

}