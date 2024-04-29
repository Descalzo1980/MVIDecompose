package dev.stas.mvidecompose.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.stas.mvidecompose.core.componentScope
import dev.stas.mvidecompose.data.RepositoryImpl
import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.domain.GetContactsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultContactListComponent(
    componentContext: ComponentContext,
    val onEditingContactRequested: (Contact) -> Unit,
    val onAddContactRequested: () -> Unit,
) : ContactListComponent, ComponentContext by componentContext {

    private val viewModel = instanceKeeper.getOrCreate { FakeViewModel() }

    private val repository = RepositoryImpl
    private val getContactsUseCase = GetContactsUseCase(repository)
    private val scope = componentScope()

    private val _state = MutableValue(State())
    val state: Value<State> = _state

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

data class State(val count: Int = 0)

private class FakeViewModel(): InstanceKeeper.Instance {

    override fun onDestroy() {
        super.onDestroy()
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