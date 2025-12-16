package com.example.room.ui.contactscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.ui.contactscreen.components.ContactEvent
import com.example.room.data.ContactDao
import com.example.room.domain.Contact
import com.example.room.ui.contactscreen.components.ContactState
import com.example.room.ui.contactscreen.components.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ContactViewModel(
    private val dao: ContactDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.LAST_NAME -> dao.getContactsOrderedByLastName()
                SortType.FIRST_NAME -> dao.getContactsOrderedByFirstName()
                SortType.PHONE_NUMBER -> dao.getContactsOrderedByPhoneNumber()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )

    }.stateIn(viewModelScope,  SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }

            ContactEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = false
                    )
                }
            }

            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber

                val isFirstNameMissing = firstName.isBlank()
                val isPhoneNumberMissing = phoneNumber.isBlank()

                if (isFirstNameMissing || isPhoneNumberMissing) {
                    _state.update {
                        it.copy(
                            isFirstNameMissing = isFirstNameMissing,
                            isPhoneNumberMissing = isPhoneNumberMissing
                        )
                    }
                    return
                }

                val contact = Contact(
                    firstName = firstName,
                    lastName = if (lastName.isNullOrBlank()) null else lastName,
                    phoneNumber = phoneNumber
                )
                viewModelScope.launch {
                    dao.upsertContact(contact)
                }
                _state.update { it.copy(
                    isAddingContact = false,
                    firstName = "",
                    lastName = null,
                    phoneNumber = "",
                    isFirstNameMissing = false,
                    isPhoneNumberMissing = false
                ) }
            }

            is ContactEvent.SetFirstName -> {
                _state.update {
                    it.copy(
                        firstName = event.firstName,
                        isFirstNameMissing = false
                    )
                }
            }

            is ContactEvent.SetLastName -> {
                _state.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }

            is ContactEvent.SetPhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber,
                        isPhoneNumberMissing = false
                    )
                }
            }

            ContactEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }
            }

            is ContactEvent.SortContact -> {
                _sortType.value = event.sortType
            }

        }
    }
}