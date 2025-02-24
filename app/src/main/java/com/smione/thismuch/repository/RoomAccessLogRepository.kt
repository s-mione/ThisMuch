package com.smione.thismuch.repository

import android.content.Context
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smione.thismuch.converter.AccessLogListElementAccessLogEntityConverter
import com.smione.thismuch.repositorycontract.AccessLogEntity
import com.smione.thismuch.repositorycontract.AccessLogRepositoryContract
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

class RoomAccessLogRepository(val context: Context) : AccessLogRepositoryContract {

    private val db = Room.databaseBuilder(
        context,
        RoomAccessLogRepositoryDatabase::class.java, "AccessLogRepository"
    ).build().roomAccessLogDao()

    override fun getHeaders(): List<String> {
        return listOf("No.", "Time On", "Time Off", "Total Time")
    }

    override fun getAccessList(): List<AccessLogListElement> {
        val accessLogList = db.getAll().map {
            AccessLogListElementAccessLogEntityConverter.fromAccessEntityToAccessListElement(it)
        }
        Log.v("RoomAccessLogRepository", "getAccessList: $accessLogList")
        return accessLogList;
    }

    override fun saveLog(element: AccessLogEntity) {
        Log.v("RoomAccessLogRepository", "saving element: $element")
        db.insertAll(element)
    }

}

@Database(entities = [AccessLogEntity::class], version = 1)
abstract class RoomAccessLogRepositoryDatabase : RoomDatabase() {
    abstract fun roomAccessLogDao(): RoomAccessLogDao
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