package com.example.room.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room.domain.Contact
import com.example.room.data.ContactDao

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {

    abstract val dao: ContactDao
}