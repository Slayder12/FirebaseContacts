package com.example.firebasecontacts

import java.io.Serializable


class User(
    val name: String? = null,
    val phoneNumber: String? = null
): Serializable {

    companion object{
        val users = mutableListOf(
            User("Иван Иванов", "+7(123)456-78-90"),
            User("Мария Петрова", "+7(234)567-89-01"),
            User("Алексей Смирнов", "+7(345)678-90-12"),
            User("Елена Кузнецова", "+7(456)789-01-23"),
            User("Дмитрий Попов", "+7(567)890-12-34"),
            User("Ольга Васильева", "+7(678)901-23-45"),
            User("Сергей Соколов", "+7(789)012-34-56"),
            User("Анна Михайлова", "+7(890)123-45-67"),
            User("Павел Новиков", "+7(901)234-56-78"),
            User("Татьяна Федорова", "+7(012)345-67-89"),
        )
    }

    override fun toString(): String {
        return "$name, $phoneNumber"
    }
}