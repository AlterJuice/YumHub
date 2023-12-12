package com.alterjuice.domain.model.user

enum class UserSex {
    MALE, FEMALE, UNSPECIFIED;

    companion object {
        fun getByName(name: String?) = UserSex.values().find {
            it.name.equals(name, true)
        }
    }
}