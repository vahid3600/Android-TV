package ir.net_box.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.net_box.test.data.local.model.VideoEntity

@Database(entities = [VideoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}