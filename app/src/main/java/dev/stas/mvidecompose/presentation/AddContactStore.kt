package dev.stas.mvidecompose.presentation

import com.arkivanov.mvikotlin.core.store.Store

interface AddContactStore :
    Store<AddContactStore.Intent, AddContactStore.State, AddContactStore.Label> {

    data class State(
        val username: String,
        val phone: String
    )

    sealed interface Label {

        object ContactSaved : Label
    }

    sealed interface Intent {

        data class ChangeUsername(val username: String) : Intent

        data class ChangePhone(val phone: String) : Intent

        object SaveContact : Intent
    }
}

/**
 * В а.п. MVI за взаимодействие view и бизнес логики отвечает Store
 * Store имеет одну точку входа и одну точку выхода
 * store.stateFlow это подписались и отрисовываем
 * accept принимает интент
 * label это для чего то одноразовгого
 *
 * */