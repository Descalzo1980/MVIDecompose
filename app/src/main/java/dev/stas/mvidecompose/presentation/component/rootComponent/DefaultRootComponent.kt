package dev.stas.mvidecompose.presentation.component.rootComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import dev.stas.mvidecompose.domain.Contact
import dev.stas.mvidecompose.presentation.component.addContactComponent.DefaultAddContactComponent
import dev.stas.mvidecompose.presentation.component.contactListComponent.DefaultContactListComponent
import dev.stas.mvidecompose.presentation.component.editContactComponent.DefaultEditContactComponent
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.ContactList,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            Config.AddContact -> {
                val component = DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                RootComponent.Child.AddContact(component)
            }

            Config.ContactList -> {
                val component = DefaultContactListComponent(
                    componentContext = componentContext,
                    onAddContactRequested = {
                        navigation.push(Config.AddContact)
                    },
                    onEditingContactRequested = {
                        navigation.push(Config.EditContact(it))
                    }
                )
                RootComponent.Child.ContactList(component)
            }

            is Config.EditContact -> {
                val component = DefaultEditContactComponent(
                    componentContext = componentContext,
                    contact = config.contact,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                RootComponent.Child.EditContact(component)
            }
        }
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        data object ContactList : Config

        @Parcelize
        data object AddContact : Config

        @Parcelize
        data class EditContact(val contact: Contact) : Config
    }
}