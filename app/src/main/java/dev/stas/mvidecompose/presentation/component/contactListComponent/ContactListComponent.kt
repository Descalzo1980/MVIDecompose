package dev.stas.mvidecompose.presentation.component.contactListComponent

import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.presentation.store.ContactListStore
import kotlinx.coroutines.flow.StateFlow

interface ContactListComponent {

    val model: StateFlow<ContactListStore.State>

    fun onContactClick(contact: Contact)

    fun onAddContactClicked()
}