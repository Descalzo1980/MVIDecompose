package dev.stas.mvidecompose.presentation.component.editContactComponent

import dev.stas.mvidecompose.presentation.store.EditContactStore
import kotlinx.coroutines.flow.StateFlow

interface EditContactComponent {

    val model: StateFlow<EditContactStore.State>

    fun onUserNameChange(username: String)
    fun onPhoneChange(phone: String)
    fun onSaveContactClicked()

}