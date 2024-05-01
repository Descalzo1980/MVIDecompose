package dev.stas.mvidecompose.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.stas.mvidecompose.core.componentScope
import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.presentation.store.ContactListStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultContactListComponent(
    componentContext: ComponentContext,
    val onEditingContactRequested: (Contact) -> Unit,
    val onAddContactRequested: () -> Unit,
) : ContactListComponent, ComponentContext by componentContext {

    private lateinit var store: ContactListStore

    init {
        componentScope().launch {
            store.labels.collect{
                when(it) {
                    ContactListStore.Label.AddContact -> {
                        onAddContactRequested()
                    }
                    is ContactListStore.Label.EditContact -> {
                        onEditingContactRequested(it.contact)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ContactListStore.State>
        get() = store.stateFlow

    override fun onContactClick(contact: Contact) {
        store.accept(ContactListStore.Intent.SelectContact(contact))
    }

    override fun onAddContactClicked() {
        store.accept(ContactListStore.Intent.AddContact)
    }
}


/**
 * CC
 *
 * LifecycleOwner, предоставляемый библиотекой Essenty, поэтому каждый компонент имеет собственный жизненный цикл.
 * StateKeeperOwner, предоставляемый библиотекой Essenty, поэтому вы можете сохранить любое состояние во время изменений конфигурации и/или смерти процесса.
 * InstanceKeeperOwner, предоставляемый библиотекой Essenty, поэтому вы можете сохранять произвольные экземпляры объектов в своих компонентах (например, в AndroidX ViewModels).
 * BackHandlerOwner, предоставляемый библиотекой Essenty, поэтому каждый компонент может обрабатывать события кнопки «Назад».
 * */