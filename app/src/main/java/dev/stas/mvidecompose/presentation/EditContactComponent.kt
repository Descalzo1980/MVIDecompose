package dev.stas.mvidecompose.presentation

import kotlinx.coroutines.flow.StateFlow

interface EditContactComponent {

    val model: StateFlow<Model>

    fun onUserNameChange(username: String)
    fun onPhoneChange(phone: String)
    fun onSaveContactClicked()

    data class Model(
        val username: String,
        val phone: String
    )
}