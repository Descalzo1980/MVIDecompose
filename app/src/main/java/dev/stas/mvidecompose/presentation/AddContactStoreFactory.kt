package dev.stas.mvidecompose.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.stas.mvidecompose.domain.AddContactUseCase

class AddContactStoreFactory(
    private val storeFactory: StoreFactory,
    private val addContactUseCase: AddContactUseCase
) {

    private val store: Store<AddContactStore.Intent, AddContactStore.State, AddContactStore.Label> =
        storeFactory.create(
            name = "AddContactStore",
            initialState = AddContactStore.State("", ""),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        )

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUserName(val username: String) : Msg
        data class ChangePhone(val phone: String) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AddContactStore.Intent, Action, AddContactStore.State, Msg, AddContactStore.Label>() {
        override fun executeIntent(
            intent: AddContactStore.Intent,
            getState: () -> AddContactStore.State
        ) {
            when (intent) {
                is AddContactStore.Intent.ChangePhone -> {
                    dispatch(Msg.ChangePhone(intent.phone))
                }

                is AddContactStore.Intent.ChangeUserName -> {
                    dispatch(Msg.ChangeUserName(intent.username))
                }

                AddContactStore.Intent.SaveContact -> {
                    val state = getState()
                    addContactUseCase(state.username,state.phone)
                    publish(AddContactStore.Label.ContactSaved)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<AddContactStore.State, Msg> {

        override fun AddContactStore.State.reduce(msg: Msg) = when (msg) {
            is Msg.ChangePhone -> {
                copy(phone = msg.phone)
            }

            is Msg.ChangeUserName -> {
                copy(phone = msg.username)
            }
        }
    }
}
/*        override fun AddContactStore.State.reduce(msg: Msg): AddContactStore.State {
            val oldState = this
            return when(msg){
                is Msg.ChangePhone -> {
                    oldState.copy(phone = msg.phone)
                }
                is Msg.ChangeUserName -> {
                    oldState.copy(phone = msg.username)
                }
            }
        }* это одно и то же/ */