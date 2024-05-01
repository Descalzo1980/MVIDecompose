package dev.stas.mvidecompose.presentation.factory

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.domain.GetContactsUseCase
import dev.stas.mvidecompose.presentation.store.ContactListStore
import kotlinx.coroutines.launch

class ContactListStoreFactory(
    private val storeFactory: StoreFactory,
    private val getContactsUseCase: GetContactsUseCase
) {

    fun create(contact: Contact): ContactListStore = object : ContactListStore,
        Store<ContactListStore.Intent, ContactListStore.State, ContactListStore.Label> by
        storeFactory.create(
            name = "ContactListStore",
            initialState = ContactListStore.State(listOf()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ){}

    private sealed interface Action {
        data class ContactsLoaded(val contacts: List<Contact>) : Action
    }

    private sealed interface Msg {
        data class ContactsLoaded(val contacts: List<Contact>) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ContactListStore.Intent, Action, ContactListStore.State, Msg, ContactListStore.Label>() {

        override fun executeAction(action: Action, getState: () -> ContactListStore.State) {
            when (action) {
                is Action.ContactsLoaded -> {
                    dispatch(Msg.ContactsLoaded(action.contacts))
                }
            }
        }

        override fun executeIntent(
            intent: ContactListStore.Intent,
            getState: () -> ContactListStore.State
        ) {
            when (intent) {
                ContactListStore.Intent.AddContact -> {
                    publish(ContactListStore.Label.AddContact)
                }

                is ContactListStore.Intent.SelectContact -> {
                    publish(ContactListStore.Label.EditContact(intent.contact))
                }
            }
        }
    }

    private inner class BootstrapperImpl: CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getContactsUseCase().collect {
                    dispatch(Action.ContactsLoaded(it))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<ContactListStore.State, Msg> {
        override fun ContactListStore.State.reduce(msg: Msg) = when (msg) {
            is Msg.ContactsLoaded -> {
                copy(contactList = contactList)
            }
        }
    }
}