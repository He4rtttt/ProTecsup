package com.example.protecsup

import android.provider.ContactsContract.CommonDataKinds.Phone

data class Users(
    var name : String? = null,
    var email : String? = null,
    var phone : String? = null,
    var uid : String? = null
)
