package com.pat.flerbi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FirstUser(val username: String, val uid: String, val location: String, val roomKey:String): Parcelable
{
constructor(): this("", "","","" )
}