package com.example.room.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

import androidx.room.Room

import com.example.room.data.ContactDatabase
import com.example.room.ui.contactscreen.ContactScreen
import com.example.room.ui.contactscreen.ContactViewModel
import com.example.room.ui.theme.RoomTheme


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contacts.db"
        ).build()
    }
    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return ContactViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomTheme {
                val state by viewModel.state.collectAsState()
                ContactScreen(
                    modifier = Modifier,
                    state = state,
                    onEvent = viewModel::onEvent,
                )
            }
        }
    }
}