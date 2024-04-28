package dev.stas.mvidecompose.presentation

import androidx.compose.runtime.collectAsState
import dev.stas.mvidecompose.data.RepositoryImpl
import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.domain.GetContactsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultContactListComponent(
    val onEditingContactRequested: (Contact) -> Unit,
    val onAddContactRequested: () -> Unit,
) : ContactListComponent {

    private val repository = RepositoryImpl
    private val getContactsUseCase = GetContactsUseCase(repository)
    private val scope = CoroutineScope(Dispatchers.Main.immediate)
    override val model: StateFlow<ContactListComponent.Model> =
        getContactsUseCase()
            .map { ContactListComponent.Model(it) }
            .stateIn(
                scope = scope,
                started = SharingStarted.Lazily,
                initialValue = ContactListComponent.Model(listOf())
            )

    override fun onContactClick(contact: Contact) {
        onEditingContactRequested(contact)
    }

    override fun onAddContactClicked() {
        onAddContactRequested()
    }
}