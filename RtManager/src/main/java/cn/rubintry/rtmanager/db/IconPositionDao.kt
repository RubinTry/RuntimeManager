package cn.rubintry.rtmanager.db

import androidx.room.*


@Dao
interface IconPositionDao {

    @Query("SELECT * FROM runtime_icon_position")
    fun getAll() : List<IconPos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg iconPos: IconPos)



    @Update
    fun update(iconPos : IconPos)

}