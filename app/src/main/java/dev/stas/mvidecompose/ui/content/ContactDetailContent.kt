package dev.stas.mvidecompose.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.stas.mvidecompose.presentation.component.addContactComponent.AddContactComponent
import dev.stas.mvidecompose.presentation.component.editContactComponent.EditContactComponent


@Composable
fun AddContact(
    component: AddContactComponent
) {
    val model by component.model.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = model.username,
            placeholder = {
                Text(text = "Username:")
            },
            onValueChange = { component.onUserNameChange(it) }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = model.phone,
            placeholder = {
                Text(text = "Phone:")
            },
            onValueChange = { component.onPhoneChange(it) }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                component.onSaveContactClicked()
            }
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun EditContact(
    component: EditContactComponent
) {
    val model by component.model.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = model.username,
            placeholder = {
                Text(text = "Username:")
            },
            onValueChange = { component.onUserNameChange(it) }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = model.phone,
            placeholder = {
                Text(text = "Phone:")
            },
            onValueChange = { component.onPhoneChange(it) }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                component.onSaveContactClicked()
            }
        ) {
            Text(text = "Save")
        }
    }
}