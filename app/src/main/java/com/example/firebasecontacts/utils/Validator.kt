package com.example.firebasecontacts.utils

import android.content.Context
import android.widget.Toast

class Validator(private val context: Context) {
    fun signUpValidate(email: String, password: String, confirmPass: String): Boolean{
        if (email.isBlank() && password.isBlank() && confirmPass.isBlank()) {
            toast("Адрес электронной почты и пароль не могут быть пустыми")
            return false
        }
        if (email.isBlank()) {
            toast("Введите email")
            return false
        }
        if (!isValidEmail(email)){
            toast("Некорректный ввод")
        }

        if (password.isBlank()) {
            toast("Введите пароль")
            return false
        }

        if (password.length < 6) {
            toast("Пароль должен сосотоять минимум из 6 символов")
            return false
        }

        if (confirmPass.isBlank()) {
            toast("Повторно введите пароль")
            return false
        }

        if (password != confirmPass){
            toast("Пароли не совпадают")
            return false
        }
        return true
    }

    fun loginValidate(email: String, password: String): Boolean{
        if (email.isBlank() && password.isBlank()) {
            toast("Адрес электронной почты и пароль не могут быть пустыми")
            return false
        }
        if (email.isBlank()) {
            toast("Введите email")
            return false
        }
        if (!isValidEmail(email)){
            toast("Некорректный ввод")
            return false
        }

        if (password.isBlank()) {
            toast("Введите пароль")
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {

        val atIndex = email.indexOf('@')
        if (atIndex == -1) {
            return false
        }

        val localPart = email.substring(0, atIndex)
        if (localPart.length < 2) {
            return false
        }

        val domainPart = email.substring(atIndex + 1)
        if (domainPart.isEmpty() || !domainPart.contains(".")) {
            return false
        }

        val domainParts = domainPart.split(".")
        if (domainParts.size < 2 || domainParts.any { it.isEmpty() }) {
            return false
        }

        return true
    }

    fun userValidate(name: String, phoneNumber: String): Boolean {
        if (name.isBlank() && phoneNumber.isBlank()) {
            toast("Имя и номер телефона не могут быть пустыми")
            return false
        }
        if (name.isBlank()) {
            toast("Введите Имя")
            return false
        }
        if (name.length !in 2..32) {
            toast("Введите корректное имя")
            return false
        }
        if (phoneNumber.isBlank()) {
            toast("Введите номер телефона")
            return false
        }
        if (phoneNumber.length !in 11..16) {
            toast("Введите корректный номер")
            return false
        }
        return true
    }

    private fun toast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

}