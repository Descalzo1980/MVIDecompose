package dev.stas.mvidecompose.presentation_legacy

import androidx.lifecycle.ViewModel
import dev.stas.mvidecompose.data.RepositoryImpl
import dev.stas.mvidecompose.domain.AddContactUseCase
import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.domain.DeleteContactUseCase
import dev.stas.mvidecompose.domain.EditContactUseCase

class ContactDetailViewModel: ViewModel() {

    private val repository = RepositoryImpl

    private val addContactUseCase = AddContactUseCase(repository)
    private val editContactUseCase = EditContactUseCase(repository)

    fun addContact(username: String, phone: String) {
        addContactUseCase(username, phone)
    }

    fun editContact(contact: Contact) {
        editContactUseCase(contact)
    }
}