package com.pat.flerbi.model

 class UserMessage(val id: String,  val msg: String,    val fromID: String, val toID: String)
{
    constructor() : this("","","", "")
}