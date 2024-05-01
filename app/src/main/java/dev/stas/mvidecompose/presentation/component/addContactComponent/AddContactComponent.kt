package dev.stas.mvidecompose.presentation.component.addContactComponent

import dev.stas.mvidecompose.presentation.store.AddContactStore
import kotlinx.coroutines.flow.StateFlow

interface AddContactComponent {

    val model: StateFlow<AddContactStore.State>

    fun onUserNameChange(username: String)
    fun onPhoneChange(phone: String)
    fun onSaveContactClicked()
}