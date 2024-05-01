package dev.stas.mvidecompose.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.domain.EditContactUseCase

class EditContactStoreFactory(
    private val storeFactory: StoreFactory,
    private val editContactUseCase: EditContactUseCase
) {

    private val store: Store<EditContactStore.Intent,EditContactStore.State,EditContactStore.Label> =
        storeFactory.create(
            name = "EditContactStore",
            initialState = EditContactStore.State("", ""),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        )

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUserName(val username: String) : Msg
        data class ChangePhone(val phone: String) : Msg
    }

    private inner class ExecutorImpl:
    CoroutineExecutor<EditContactStore.Intent, Action,EditContactStore.State,Msg,EditContactStore.Label>(){

        override fun executeIntent(
            intent: EditContactStore.Intent,
            getState: () -> EditContactStore.State
        ) {
            when(intent){
                is EditContactStore.Intent.ChangePhone -> {
                    dispatch(Msg.ChangePhone(intent.phone))
                }
                is EditContactStore.Intent.ChangeUsername -> {
                    dispatch(Msg.ChangePhone(intent.username))
                }
                EditContactStore.Intent.SaveContact -> {
                    val state = getState()
                    val contact = Contact(username = state.username, phone = state.phone)
                    editContactUseCase(contact = contact)
                    publish(EditContactStore.Label.ContactSaved)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<EditContactStore.State, Msg> {
        override fun EditContactStore.State.reduce(msg: Msg) =
            when(msg) {
                is Msg.ChangePhone -> {
                    copy(phone = msg.phone)
                }
                is Msg.ChangeUserName -> {
                    copy(username = msg.username)
                }
            }
        }
    }