package dev.stas.mvidecompose.presentation

import kotlinx.coroutines.flow.StateFlow

interface EditContactComponent {

    val model: StateFlow<EditContactStore.State>

    fun onUserNameChange(username: String)
    fun onPhoneChange(phone: String)
    fun onSaveContactClicked()

}