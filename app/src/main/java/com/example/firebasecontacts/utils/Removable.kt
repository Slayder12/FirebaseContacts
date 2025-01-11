package com.example.firebasecontacts.utils

import com.example.firebasecontacts.models.UserModel

interface Removable {
    fun remove(user: UserModel?)
}