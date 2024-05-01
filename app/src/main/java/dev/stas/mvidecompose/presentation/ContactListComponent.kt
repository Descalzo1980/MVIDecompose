package dev.stas.mvidecompose.presentation

import dev.stas.mvidecompose.domain.Contact
import kotlinx.coroutines.flow.StateFlow

interface ContactListComponent {

    val model: StateFlow<ContactListStore.State>

    fun onContactClicked(contact: Contact)

    fun onAddContactClicked()
}