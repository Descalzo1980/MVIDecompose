package dev.stas.mvidecompose.presentation.component.rootComponent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.stas.mvidecompose.presentation.component.addContactComponent.AddContactComponent
import dev.stas.mvidecompose.presentation.component.contactListComponent.ContactListComponent
import dev.stas.mvidecompose.presentation.component.editContactComponent.EditContactComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>
    sealed interface Child {

        class AddContact(val component: AddContactComponent): Child

        class ContactList(val component: ContactListComponent): Child

        class EditContact(val component: EditContactComponent): Child
    }
}