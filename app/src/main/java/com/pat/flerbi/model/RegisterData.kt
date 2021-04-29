package com.pat.flerbi.model

data class RegisterData(val username: String, val uid: String, val points: Int, val recommends: Int, val achievements:Int, val banned: Int)
{
    constructor(): this("","", 0, 0, 0, 0)
}
