package com.swensun.func.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RoomDataBaseUtils
import com.swensun.func.room.database.RoomEntity

class RoomViewModel : ViewModel() {

    val roomDao by lazy { RoomDataBaseUtils.getRoomDao() }

    val roomQueryLiveData = roomDao.queryRooms()

    
    fun add(id: Int) {
        roomDao.insertRoom(listOf(RoomEntity().apply {
            this.id = id
        }))
    }

    fun update(id: Int) {
        roomDao.updateRoom(RoomEntity().apply {
            this.id = id
        })
    }

    fun delete(id: Int) {
        roomDao.deleteRoom(RoomEntity().apply {
            this.id = id
        })
    }
    
}
