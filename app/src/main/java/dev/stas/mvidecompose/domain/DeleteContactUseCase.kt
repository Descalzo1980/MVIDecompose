package dev.stas.mvidecompose.domain

class DeleteContactUseCase(
    private val repository: Repository
) {

    operator fun invoke(
        contact: Contact
    ){
       repository.deleteContact(contact)
    }
}