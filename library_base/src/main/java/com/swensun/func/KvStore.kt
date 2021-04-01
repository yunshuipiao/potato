package com.swensun.func

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.*
import com.google.gson.Gson
import com.swensun.base.BaseDao
import com.swensun.swutils.SwUtils

/**
 * author : zp
 * date : 2021/3/31
 * Potato
 */


/**
 * 针对 sp 的缺点，使用数据库 room 来替代, 并且天然支持 livedata 通知
 */

@Database(entities = [KeyValue::class], version = 1, exportSchema = true)
abstract class KvStore : RoomDatabase() {
    companion object {
        val database by lazy {
            Room.databaseBuilder(
                SwUtils.application, KvStore::class.java, "key_value_database.db"
            )
                .allowMainThreadQueries()
                .build()
        }

        fun <T: Any> get(key: String, defaultValue: T): T {
            val type = defaultValue::class.simpleName ?: ""
            val value = database.dao().get("${key}_${type}")
            return try {
                val v  = when(defaultValue) {
                    is String -> value
                    is Boolean -> value == "true"
                    is Int -> value.toInt()
                    is Float -> value.toFloat()
                    is Double -> value.toDouble()
                    else -> Gson().fromJson(value, defaultValue::class.java)
                }
                v as T
            } catch (e: Throwable) {
                defaultValue
            }
        }

        fun <T: Any> set(key: String, value: T) {
            val type = value::class.simpleName ?: ""
            val v = when(value) {
                is String, is Boolean, is Int, is Float, is Double -> value.toString()
                else -> Gson().toJson(value)
            }
            database.dao().insertReplace(KeyValue("${key}_${type}", v))
        }

       inline fun <reified T> liveData(key: String, sticky: Boolean = true): MediatorLiveData<T> {
           var realSticky = sticky
            val mld = MediatorLiveData<T>()
            mld.addSource(database.dao().liveData("${key}_${T::class.simpleName}")) {
                if (realSticky) {
                    mld.value = try {
                        Gson().fromJson(it, T::class.java)
                    } catch (e: Throwable) {
                        null
                    }
                } else {
                    realSticky = true
                }
            }
            return mld
        }
    }
    abstract fun dao(): KeyValueDao
}

@Entity()
data class KeyValue(
    @PrimaryKey
    @ColumnInfo(name = "key", defaultValue = "")
    var key: String = "",
    @ColumnInfo(name = "value", defaultValue = "")
    var value: String = ""
)

@Dao
abstract class KeyValueDao : BaseDao<KeyValue>() {
    @Query("SELECT value from KeyValue WHERE `key` = :key")
    abstract fun get(key: String): String

    @Query("SELECT value from KeyValue WHERE `key` = :key")
    abstract fun liveData(key: String): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertReplace(obj: KeyValue): Long
}





