package com.smione.thismuch.model.repository

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smione.thismuch.model.repository.entity.AccessLogEntity

class RoomAccessLogRepository(val context: Context) : AccessLogRepositoryInterface {

    private val db = RoomAccessLogRepositoryDatabase.getDatabase(context).roomAccessLogDao()

    override fun getHeaders(): List<String> {
        return listOf("No.", "Time On", "Time Off", "Total Time")
    }

    override suspend fun saveAccessLogEntity(element: AccessLogEntity) {
        db.insertAll(element)
    }

    override suspend fun getAccessLogList(): List<AccessLogEntity> {
        return db.getAll()
    }

    override suspend fun getAccessLogListSortedByTimeDesc(): List<AccessLogEntity> {
        return db.getAllSortedByTimeDesc()
    }

    override suspend fun deleteAll() {
        db.deleteAll()
    }
}

@Database(entities = [AccessLogEntity::class], version = 1)
abstract class RoomAccessLogRepositoryDatabase : RoomDatabase() {
    abstract fun roomAccessLogDao(): RoomAccessLogDao

    companion object {
        @Volatile
        private var dbSingleton: RoomAccessLogRepositoryDatabase? = null

        fun getDatabase(context: Context): RoomAccessLogRepositoryDatabase {
            return dbSingleton ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomAccessLogRepositoryDatabase::class.java,
                    "AccessLogRepository"
                ).build()
                dbSingleton = instance
                instance
            }
        }
    }
}

@Dao
interface RoomAccessLogDao {

    @Query("SELECT * FROM AccessLogEntity")
    fun getAll(): List<AccessLogEntity>

    @Query("SELECT * FROM AccessLogEntity ORDER BY time_off DESC")
    fun getAllSortedByTimeDesc(): List<AccessLogEntity>

    @Insert
    fun insertAll(vararg users: AccessLogEntity)

    @Delete
    fun delete(user: AccessLogEntity)

    @Query("DELETE FROM AccessLogEntity")
    fun deleteAll()
}