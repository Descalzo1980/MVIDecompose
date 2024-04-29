package dev.stas.mvidecompose.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.consume
import dev.stas.mvidecompose.data.RepositoryImpl
import dev.stas.mvidecompose.domain.AddContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultAddContactComponent(
    componentContext: ComponentContext,
    private val onContactSaved: () -> Unit
) : AddContactComponent, ComponentContext by componentContext {

    private val repository = RepositoryImpl
    private val addContactUseCase = AddContactUseCase(repository)

    init {
        stateKeeper.register(KEY){
            model.value
        }
    }

    private val _model = MutableStateFlow(
        stateKeeper.consume(KEY) ?: AddContactComponent.Model(
            username = "",
            phone = ""
        )
    )

    override val model: StateFlow<AddContactComponent.Model>
        get() = _model.asStateFlow()

    override fun onUserNameChange(username: String) {
        _model.value = model.value.copy(username = username)
    }

    override fun onPhoneChange(phone: String) {
        _model.value = model.value.copy(phone = phone)
    }

    override fun onSaveContactClicked() {
        val (username, phone) = model.value
        addContactUseCase(username, phone)
        onContactSaved()
    }

    companion object {
       private const val KEY = "DefaultAddContactComponent"
    }
}