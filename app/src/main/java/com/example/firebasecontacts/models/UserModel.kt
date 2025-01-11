package com.example.firebasecontacts.models

import java.io.Serializable

class UserModel(
    val key: String? = null,
    val name: String? = null,
    val phoneNumber: String? = null
): Serializable {

    companion object{
        val users = mutableListOf(
            UserModel(null,"Иван Иванов", "+7(123)456-78-90"),
            UserModel(null,"Мария Петрова", "+7(234)567-89-01"),
            UserModel(null,"Алексей Смирнов", "+7(345)678-90-12"),
            UserModel(null,"Елена Кузнецова", "+7(456)789-01-23"),
            UserModel(null,"Дмитрий Попов", "+7(567)890-12-34"),
            UserModel(null,"Ольга Васильева", "+7(678)901-23-45"),
            UserModel(null,"Сергей Соколов", "+7(789)012-34-56"),
            UserModel(null,"Анна Михайлова", "+7(890)123-45-67"),
            UserModel(null,"Павел Новиков", "+7(901)234-56-78"),
            UserModel(null,"Татьяна Федорова", "+7(012)345-67-89"),
        )
    }

    override fun toString(): String {
        return "$name, $phoneNumber"
    }
}