package dev.stas.mvidecompose.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import dev.stas.mvidecompose.presentation.DefaultRootComponent
import dev.stas.mvidecompose.presentation.RootComponent
import dev.stas.mvidecompose.ui.theme.MVIDecomposeTheme

@Composable
fun RootContent(
    component: DefaultRootComponent
) {
    MVIDecomposeTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Children(
                stack = component.stack
            ) {
                when(val instance = it.instance){

                    is RootComponent.Child.AddContact -> {
                        AddContact(component = instance.component)
                    }
                    is RootComponent.Child.ContactList -> {
                        Contacts(component = instance.component)
                    }
                    is RootComponent.Child.EditContact -> {
                        EditContact(component = instance.component)
                    }
                }
            }
        }
    }
}