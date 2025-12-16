package com.example.room.ui.contactscreen.components

import com.example.room.domain.Contact

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String? = null,
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val isPhoneNumberMissing: Boolean = false,
    val isFirstNameMissing: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)