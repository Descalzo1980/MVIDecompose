package dev.stas.mvidecompose.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.stas.mvidecompose.domain.Contact

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    val navigation = StackNavigation<Config>()

    fun child(
        componentContext: ComponentContext,
        config: Config
    ): ComponentContext {
        return when (config) {
            Config.AddContact -> {
                DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
            }

            Config.ContactList -> {
                DefaultContactListComponent(
                    componentContext = componentContext,
                    onAddContactRequested = {
                        navigation.push(Config.AddContact)
                    },
                    onEditingContactRequested = {
                        navigation.push(Config.EditContact(it))
                    }
                )
            }

            is Config.EditContact -> {
                DefaultEditContactComponent(
                    componentContext = componentContext,
                    contact = config.contact,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
            }
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object ContactList : Config

        @Parcelize
        data object AddContact : Config

        @Parcelize
        data class EditContact(val contact: Contact) : Config
    }
}