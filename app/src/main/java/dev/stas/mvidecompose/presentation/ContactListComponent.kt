package dev.stas.mvidecompose.presentation

import dev.stas.mvidecompose.domain.Contact
import kotlinx.coroutines.flow.StateFlow

interface ContactListComponent {

    val model: StateFlow<Model>

    fun onContactClick(contact: Contact)

    fun onAddContactClicked()
    data class Model(
        val contactList: List<Contact>
    )
}