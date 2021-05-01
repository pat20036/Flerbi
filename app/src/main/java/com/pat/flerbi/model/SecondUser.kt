package com.pat.flerbi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class SecondUser(val username: String, val uid: String, val location: String)
{
constructor(): this("", "","" )
}