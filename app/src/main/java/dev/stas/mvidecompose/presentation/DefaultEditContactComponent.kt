package dev.stas.mvidecompose.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.stas.mvidecompose.core.componentScope
import dev.stas.mvidecompose.domain.Contact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultEditContactComponent(
    componentContext: ComponentContext,
    private val contact: Contact,
    private val onContactSaved: () -> Unit
) : EditContactComponent, ComponentContext by componentContext {

    private lateinit var store: EditContactStore

    init {
        componentScope().launch {
            store.labels.collect {
                when(it) {
                    EditContactStore.Label.ContactSaved -> {
                        onContactSaved()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<EditContactStore.State>
        get() = store.stateFlow

    override fun onUserNameChange(username: String) {
        store.accept(EditContactStore.Intent.ChangeUsername(username))
    }

    override fun onPhoneChange(phone: String) {
        store.accept(EditContactStore.Intent.ChangePhone(phone))
    }

    override fun onSaveContactClicked() {
        store.accept(EditContactStore.Intent.SaveContact)
    }
}
