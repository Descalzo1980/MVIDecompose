package dev.stas.mvidecompose.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
@Parcelize
data class Contact(
    val id: Int = -1,
    val username: String,
    val phone: String
): Parcelable
