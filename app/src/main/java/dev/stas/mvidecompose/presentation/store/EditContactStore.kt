package dev.stas.mvidecompose.presentation.store

import com.arkivanov.mvikotlin.core.store.Store

interface EditContactStore: Store<EditContactStore.Intent, EditContactStore.State, EditContactStore.Label> {

    data class State(
        val id: Int,
        val username: String,
        val phone: String
    )

    sealed interface Label{
        data object ContactSaved: Label
    }

    sealed interface Intent{

        data class ChangeUsername(val username: String): Intent
        data class ChangePhone(val phone: String): Intent
        data object SaveContact : Intent
    }
}