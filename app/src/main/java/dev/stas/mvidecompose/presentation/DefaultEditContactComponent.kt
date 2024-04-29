package dev.stas.mvidecompose.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.consume
import dev.stas.mvidecompose.data.RepositoryImpl
import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.domain.EditContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultEditContactComponent(
    componentContext: ComponentContext,
    private val contact: Contact
) : EditContactComponent, ComponentContext by componentContext {

    private val repository = RepositoryImpl
    private val editContactUseCase = EditContactUseCase(repository)

    init {
        stateKeeper.register(KEY){
            model.value
        }
    }

    private val _model = MutableStateFlow(
        stateKeeper.consume(KEY) ?: EditContactComponent.Model(
            username = contact.username,
            phone = contact.phone,
        )
    )
    override val model: StateFlow<EditContactComponent.Model>
        get() = _model.asStateFlow()

    override fun onUserNameChange(username: String) {
        _model.value = model.value.copy(username = username)
    }

    override fun onPhoneChange(phone: String) {
        _model.value = model.value.copy(phone = phone)
    }

    override fun onSaveContactClicked() {
        val (username, phone) = model.value
        editContactUseCase(
            contact.copy(
                username = username,
                phone = phone
            )
        )
    }

    companion object {
        private const val KEY = "DefaultEditContactComponent"
    }
}
