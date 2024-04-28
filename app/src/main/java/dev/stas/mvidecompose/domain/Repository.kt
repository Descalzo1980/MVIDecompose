package dev.stas.mvidecompose.domain

import kotlinx.coroutines.flow.Flow

interface Repository {

    val contacts: Flow<List<Contact>>

    fun saveContact(contact: Contact)

    fun deleteContact(contact: Contact)
}