package dev.stas.mvidecompose.presentation

import kotlinx.coroutines.flow.StateFlow

interface AddContactComponent {

    val model: StateFlow<AddContactStore.State>

    fun onUserNameChange(username: String)
    fun onPhoneChange(phone: String)
    fun onSaveContactClicked()
}