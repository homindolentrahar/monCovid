package com.homindolentrahar.moncovid.data.database

import androidx.room.*
import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult
import io.reactivex.Observable

@Database(
    entities = [CovidDailyResult::class, CovidProvinceResult::class],
    version = 1,
    exportSchema = false
)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}

@Dao
interface CacheDao {
    @Query("SELECT * FROM daily_table ORDER BY hariKe DESC")
    fun getCachedDaily(): Observable<List<CovidDailyResult>>

    @Query("SELECT * FROM province_table ORDER BY positif DESC")
    fun getCachedProvince(): Observable<List<CovidProvinceResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cacheDaily(items: List<CovidDailyResult>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cacheProvince(items: List<CovidProvinceResult>)
}
