package com.smione.thismuch.model.repository

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

class RoomAccessLogRepository(val context: Context) : AccessLogRepositoryInterface {

    private val db = RoomAccessLogRepositoryDatabase.getDatabase(context).roomAccessLogDao()

    override fun getHeaders(): List<String> {
        return listOf("No.", "Time On", "Time Off", "Total Time")
    }

    override suspend fun saveAccessLogElement(element: AccessLogEntity) {
        db.insertAll(element)
    }

    override suspend fun getAccessLogList(): List<AccessLogEntity> {
        return db.getAll()
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

    @Insert
    fun insertAll(vararg users: AccessLogEntity)

    @Delete
    fun delete(user: AccessLogEntity)
}