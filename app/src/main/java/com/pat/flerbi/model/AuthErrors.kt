package com.pat.flerbi.model

enum class RegisterError(val description:String)
{
    WRONG_PASSWORD("Passwords do not match"),
    ACCEPT_TERMS("Accept Terms of use"),
    NICKNAME_TAKEN("Nickname taken"),
    EMAIL_TAKEN("E-mail taken")
}

enum class LoginError(val description: String)
{
    EMPTY_EMAIL("E-mail cannot be empty"),
    EMPTY_PASSWORD("Password cannot be empty"),
    PASSWORD_LENGTH("Password must have at least 6 characters."),
    INCORRECT_PASSWORD("Password incorrect"),
    INCORRECT_EMAIL("E-mail incorrect"),
    USER_NOT_FOUND("User not found")
}